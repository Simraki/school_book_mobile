package yeapcool.school_book.marksStudent

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
import yeapcool.school_book.adapters.marksStudentAdapter.IMarksStudentAdapter
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.data.GetterMarks
import yeapcool.school_book.model.data.Mark
import yeapcool.school_book.model.data.User
import yeapcool.school_book.model.network.ServerRequest


class MarksStudentPresenter : IMarksStudent.Presenter {

    private var view: IMarksStudent.View? = null
    private var viewAdapter: IMarksStudentAdapter.View? = null
    private var modelAdapter: IMarksStudentAdapter.Model? = null

    private val model = Model()
    private val disposables = CompositeDisposable()

    private var subject: String? = null
    private var final = false

    private var count = 0

    private var isInitScrollTh = false
    private var load = false

    override fun unbind() {
        view = null
        viewAdapter = null
        modelAdapter = null
    }

    override fun bind(view: IMarksStudent.View, viewAdapter: IMarksStudentAdapter.View, modelAdapter: IMarksStudentAdapter.Model) {
        this.view = view
        this.viewAdapter = viewAdapter
        this.modelAdapter = modelAdapter

        getMarks(true)
    }

    override fun setSubject(subject: String) {
        this.subject = subject
        getMarks(true)
    }

    override fun setFinal(final: Boolean) {
        this.final = final
        getMarks(true)
    }

    override fun refresh() {
        getMarks(true)
    }

    private fun getMarks(clear: Boolean) {

        if (clear)
            count = 0

        val id_user = model.pref()?.getId_user()

        if (!load && id_user != null && id_user != -1) {

            view?.progress(true)
            load = true

            val user = User(id_user = id_user)
            val getter = GetterMarks(subject = subject, final = final, count = count)
            val request = ServerRequest(operation = Constants.OPERATION_GET_MARKS, getterMarks = getter, user = user)
            val th = model.network(request)

            if (th != null) {
                val list = ArrayList<Mark>()

                val disp_getMarks = th
                        .doOnTerminate { terminateGetMarks(list) }
                        .subscribe { it ->
                            val m = it.marks;
                            if (it.result == Constants.SUCCESS && m != null && !m.isEmpty()) {
                                list.addAll(m)
                            } else {
                                view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }

                disposables.add(disp_getMarks)
            }
        }
    }

    private fun terminateGetMarks(list: ArrayList<Mark>) {

        if (list.isEmpty()) {
            if (count == 0) {
                view?.visibleRv(false)
            }
        } else {
            if (count == 0) {
                modelAdapter?.setItems(list)
                modelAdapter?.notifyAdapter()
                view?.visibleRv(true)
            } else {
                modelAdapter?.addItems(list)
                modelAdapter?.notifySomeItems(count * 10)
            }

            if (!isInitScrollTh) {
                val disp_scroll = getScrollObservable()
                disposables.add(disp_scroll)
                isInitScrollTh = true
            }
            count++
        }

        view?.progress(false)
        load = false
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
                        getMarks(false)
                    }
}