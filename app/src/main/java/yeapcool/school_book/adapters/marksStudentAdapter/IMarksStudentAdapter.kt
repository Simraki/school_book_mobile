package yeapcool.school_book.adapters.marksStudentAdapter

import yeapcool.school_book.model.data.Mark


interface IMarksStudentAdapter {

    interface View {
        fun getItem(position: Int): Mark?

    }

    interface Model {
        fun setItems(items: ArrayList<Mark>)

        fun addItems(items: ArrayList<Mark>)

        fun notifyAdapter()

        fun notifySomeItems(updateSize: Int)

    }


}