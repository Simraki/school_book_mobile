package yeapcool.school_book.marksStudent

import android.support.v7.widget.RecyclerView
import yeapcool.school_book.adapters.marksStudentAdapter.IMarksStudentAdapter
import yeapcool.school_book.common.CommonPresenter
import yeapcool.school_book.common.NetworkView


interface IMarksStudent {

    interface View: NetworkView {

        var recyclerView: RecyclerView

        fun visibleRv(visible: Boolean)

    }

    interface Presenter : CommonPresenter {

        fun bind(view: IMarksStudent.View, viewAdapter: IMarksStudentAdapter.View, modelAdapter: IMarksStudentAdapter.Model)

        fun setSubject(subject: String)

        fun setFinal(final: Boolean)

        fun refresh()

        fun clearTh()

    }

}