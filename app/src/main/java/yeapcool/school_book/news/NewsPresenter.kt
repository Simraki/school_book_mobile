package yeapcool.school_book.news

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.adapters.newsAdapter.INewsAdapter
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.data.GetterNews
import yeapcool.school_book.model.data.News
import yeapcool.school_book.model.network.ServerRequest


class NewsPresenter : INews.Presenter {

    private var view: INews.View? = null
    private var viewAdapter: INewsAdapter.View? = null
    private var modelAdapter: INewsAdapter.Model? = null

    private val model = Model()
    private val disposables = CompositeDisposable()

    private var count = 0
    private var offset = false
    private var last_id = -1

    private var isInitScrollTh = false
    private var load = false

    override fun unbind() {
        view = null
        viewAdapter = null
        modelAdapter = null
    }

    override fun bind(view: INews.View, viewAdapter: INewsAdapter.View, modelAdapter: INewsAdapter.Model) {
        this.view = view
        this.viewAdapter = viewAdapter
        this.modelAdapter = modelAdapter

        getNews(true)
    }

    private fun getNews(clear: Boolean) {

        if (clear) {
            count = 0
            offset = false
            last_id = -1
        }

        if (last_id != 0 && !load) {

            view?.progress(true)
            load = true

            val getter = GetterNews(offset, last_id)
            val request = ServerRequest(operation = Constants.OPERATION_GET_NEWS, getterNews = getter)
            val th = model.network(request)

            if (th != null) {
                val list = ArrayList<News>()

                val disp_getNews = th
                        .doOnTerminate { terminateGetNews(list) }
                        .subscribe ({ it ->
                            val n = it.news
                            if (it.result == Constants.SUCCESS && n != null && !n.isEmpty()) {
                                list.addAll(n)
                                count++
                                it.getterNews?.last_id?.let { last_id = if (last_id != it) it else 0 }
                            } else {
                                view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }, {
                            view?.showSnackbar(Constants.ERROR)
                        })

                disposables.add(disp_getNews)
            }
        }
    }

    private fun terminateGetNews(list: ArrayList<News>) {

        if (list.isEmpty()) {
            if (!offset) {
                view?.visibleRv(false)
            }
        } else {
            if (!offset) {
                modelAdapter?.setItems(list)
                modelAdapter?.notifyAdapter()
                view?.visibleRv(true)
                offset = true
            } else {
                modelAdapter?.addItems(list)
                modelAdapter?.notifySomeItems(count * 10)
            }

            if (!isInitScrollTh) {
                val disp_scroll = getScrollObservable()
                disposables.add(disp_scroll)
                isInitScrollTh = true
            }
        }

        view?.progress(false)
        load = false

    }

    override fun refresh() {
        getNews(true)
    }


    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }

    private fun getScrollObservable(): Disposable =
            Observable.create<Unit>({ subscriber ->
                val listener = object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (!subscriber.isDisposed) {
                            val offset = 10 * (count - 1)
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            val position = layoutManager.findLastVisibleItemPosition()
                            val updatePosition = offset + 8

                            if (position >= updatePosition && !load) {
                                subscriber.onNext(Unit)
                            }
                        }
                    }
                }

                view?.recyclerView?.addOnScrollListener(listener)
                subscriber.setCancellable { view?.recyclerView?.removeOnScrollListener(listener) }
                if (view?.recyclerView?.adapter?.itemCount == 0 && !load)
                    subscriber.onNext(Unit)
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        getNews(false)
                    }
}