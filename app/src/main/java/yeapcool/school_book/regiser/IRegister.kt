package yeapcool.school_book.regiser

import yeapcool.school_book.common.CommonPresenter


interface IRegister {

    interface View {

        fun showToast()

        fun toStepOne()

        fun toStepTwo()

        fun toStepThree()

    }

    interface Presenter: CommonPresenter {

        fun bind(view: IRegister.View)

        fun toStepOne()

        fun toStepTwo()

        fun toStepThree()

        fun clickBtnRegister()

        fun toLogin()
    }

}