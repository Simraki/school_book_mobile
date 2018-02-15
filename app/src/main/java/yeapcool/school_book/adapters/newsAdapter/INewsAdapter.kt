package yeapcool.school_book.adapters.newsAdapter

import yeapcool.school_book.model.data.News


interface INewsAdapter {

    interface View {
        fun getItem(position: Int): News?
    }

    interface Model {
        fun setItems(items: ArrayList<News>)

        fun addItems(items: ArrayList<News>)

        fun notifyAdapter()

        fun notifySomeItems(updateSize: Int)
    }
}