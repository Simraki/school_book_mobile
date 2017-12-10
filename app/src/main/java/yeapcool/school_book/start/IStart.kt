package yeapcool.school_book.start

import yeapcool.school_book.common.CommonPresenter


interface IStart {

    interface View {

        fun toLogin()

        fun toMain()

    }

    interface Presenter: CommonPresenter {

        fun bind(view: IStart.View)

        fun isLoggedIn()

    }

}