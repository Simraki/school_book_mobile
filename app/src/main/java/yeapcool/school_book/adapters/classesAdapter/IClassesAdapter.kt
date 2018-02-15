package yeapcool.school_book.adapters.classesAdapter

import yeapcool.school_book.model.data.Link


interface IClassesAdapter {

    interface View {
        fun getItem(position: Int): Link?
    }

    interface Model {

        var onClick: ((Int) -> Unit)?

        fun setItems(items: ArrayList<Link>)

        fun notifyAdapter()
    }
}