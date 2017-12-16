package yeapcool.school_book.login

import yeapcool.school_book.common.CommonPresenter


interface ILogin {

    interface View {

        fun toRegister()

        fun errorEmail(error: String)

        fun errorPassword(error: String)

        fun showLoad()

        fun showSnackbar(message: String)

    }

    interface Presenter: CommonPresenter {

        fun bind(view: ILogin.View)

        fun clickBtnLogin(email: String, password: String)

        fun toRegister()

        fun clearTh()



    }
}