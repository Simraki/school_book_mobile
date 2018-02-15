package yeapcool.school_book.news

import android.support.v7.widget.RecyclerView
import yeapcool.school_book.adapters.newsAdapter.INewsAdapter
import yeapcool.school_book.common.CommonPresenter
import yeapcool.school_book.common.NetworkView


interface INews {

    interface View: NetworkView {

        var recyclerView: RecyclerView

        fun visibleRv(visible: Boolean)
    }

    interface Presenter: CommonPresenter {

        fun bind(view: INews.View, viewAdapter: INewsAdapter.View, modelAdapter: INewsAdapter.Model)

        fun refresh()

        fun clearTh()

    }

}