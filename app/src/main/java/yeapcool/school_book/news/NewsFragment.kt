package yeapcool.school_book.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.*
import yeapcool.school_book.R
import yeapcool.school_book.adapters.newsAdapter.NewsAdapter


class NewsFragment : Fragment(), INews.View {

    private lateinit var presenter: NewsPresenter
    private lateinit var adapter: NewsAdapter

    override lateinit var recyclerView: RecyclerView

    private var isInitRv = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        adapter = NewsAdapter(context)

        news_swipeLayout_news.setOnRefreshListener { presenter.refresh() }
        recyclerView = news_rv_news

        presenter = NewsPresenter()
    }

    override fun onResume() {
        super.onResume()
        presenter.bind(this, adapter, adapter)
    }

    override fun onPause() {
        super.onPause()
        presenter.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearTh()
    }

    override fun showSnackbar(message: String) {
        val v = view
        v?.let {
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun progress(visible: Boolean) {
        news_swipeLayout_news.isRefreshing = visible
    }


    override fun visibleRv(visible: Boolean) {
        if (visible) {
            recyclerView.visibility = View.VISIBLE
            news_tv_Nload.visibility = View.GONE
            if (!isInitRv) {
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
            }
        } else {
            news_tv_Nload.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }
}