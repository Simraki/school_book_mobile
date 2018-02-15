package yeapcool.school_book.adapters.marksTeacherAdapter

import yeapcool.school_book.model.data.User


interface IMarksTeacherAdapter {

    interface View {
        fun getItem(position: Int): User
    }

    interface Model {

        var onClickVisit: ((Int) -> Unit)?
        var onClickTwo: ((Int) -> Unit)?
        var onClickThree: ((Int) -> Unit)?
        var onClickFour: ((Int) -> Unit)?
        var onClickFive: ((Int) -> Unit)?
        var onClickDelete: ((Int) -> Unit)?

        fun setItems(items: ArrayList<User>)

        fun addMarksVisit(markOrVisit: String, position: Int)

        fun notifyAdapter()

    }
}