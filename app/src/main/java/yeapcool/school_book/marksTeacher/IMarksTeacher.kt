package yeapcool.school_book.marksTeacher

import yeapcool.school_book.adapters.classesAdapter.IClassesAdapter
import yeapcool.school_book.adapters.marksTeacherAdapter.IMarksTeacherAdapter
import yeapcool.school_book.common.CommonPresenter
import yeapcool.school_book.common.NetworkView


interface IMarksTeacher {

    interface View : NetworkView {

        fun setNameClass(nameClass: String)

        fun visibleRvClasses(visible: Boolean)

        fun visibleStepTwo(visible: Boolean)

        fun visibleRvStudents(visible: Boolean)

    }

    interface Presenter : CommonPresenter {

        fun bind(view: IMarksTeacher.View, viewAdapterClasses: IClassesAdapter.View, modelAdapterClasses: IClassesAdapter.Model,
                 viewAdapterStudents: IMarksTeacherAdapter.View, modelAdapterStudents: IMarksTeacherAdapter.Model)

        fun clearTh()

        fun clickBack()

        fun setDescription(description: String)

    }


}