package yeapcool.school_book.regiser

import yeapcool.school_book.common.CommonPresenter
import yeapcool.school_book.model.network.pojo.SchoolClass


interface IRegister {

    interface View {

        fun showSnackbar(message: String)

        fun toStepOne()

        fun toStepTwo()

        fun toStepThree()

        fun errorEmail(error: String)

        fun errorPassword(error: String)

        fun errorName(error: String)

        fun errorSurname(error: String)

        fun toLogin()

    }

    interface Presenter: CommonPresenter {

        fun bind(view: IRegister.View)

        fun clickBtnBackToStepOne()

        fun clickBtnBackToStepTwo()

        fun clickBtnToStepTwo(email: String, password: String, birthDay: String?, birthMonth: String?)

        fun clickBtnToStepThree(name: String, surname: String, typeUser: Int)

        fun clickBtnRegisterStudent(schoolClass: SchoolClass)

        fun clickBtnRegisterTeacher(speciality: ArrayList<String>?)

        fun toLogin()

        fun clearTh()
    }

}