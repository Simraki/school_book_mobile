package yeapcool.school_book.regiser

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.network.ServerRequest
import yeapcool.school_book.model.data.SchoolClass
import yeapcool.school_book.model.data.User


class RegisterPresenter : IRegister.Presenter {

    private var view: IRegister.View? = null
    private val model = Model()
    private val disposables = CompositeDisposable()

    private val user = User()

    override fun bind(view: IRegister.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }

    override fun clickBtnBackToStepOne() {
        view?.toStepOne()
    }

    override fun clickBtnBackToStepTwo() {
        view?.toStepTwo()
    }

    override fun clickBtnToStepTwo(email: String, password: String, birthDay: String?, birthMonth: String?) {

        if (email.isEmpty())
            view?.errorEmail(Constants.ERROR_EMPTY)
        else
            view?.errorEmail(Constants.NO_ERROR)

        if (password.isEmpty())
            view?.errorPassword(Constants.ERROR_EMPTY)
        else
            view?.errorPassword(Constants.NO_ERROR)

        if (!email.isEmpty() && !password.isEmpty()) {
            val birthDate = "$birthDay $birthMonth"

            user.email = email
            user.password = password
            user.birthDate = birthDate

            view?.toStepTwo()
        }
    }

    override fun clickBtnToStepThree(name: String, surname: String, typeUser: Int) {

        if (name.isEmpty())
            view?.errorName(Constants.ERROR_EMPTY)
        else
            view?.errorName(Constants.NO_ERROR)

        if (surname.isEmpty())
            view?.errorSurname(Constants.ERROR_EMPTY)
        else
            view?.errorSurname(Constants.NO_ERROR)

        if (!name.isEmpty() && !surname.isEmpty()) {
            user.name = name
            user.surname = surname
            user.type = typeUser

            view?.toStepThree()
        }
    }

    override fun clickBtnRegisterStudent(schoolClass: SchoolClass) {
        user.classStudent = schoolClass

        register()
    }

    override fun clickBtnRegisterTeacher(speciality: ArrayList<String>?) {
        if (speciality == null || speciality.isEmpty()) {
            view?.showSnackbar(Constants.ERROR_NOT_CHOSEN_SPECIALITY)
        } else {
            user.speciality = speciality

            register()
        }
    }

    private fun register() {
        val request = ServerRequest(operation = Constants.OPERATION_REGISTER, user = user)
        val th = model.network(request)

        if (th != null) {
            val disp_register = th
                    .subscribe({ it ->
                        if (it.result == Constants.SUCCESS) {
                            view?.showSnackbar(Constants.REGISTER_GOOD)
                            view?.toStepOne()
                        } else
                            view?.showSnackbar(HelpMethods.responseError(it))
                    }, {
                        view?.showSnackbar(Constants.ERROR)
                    })
            disposables.add(disp_register)
        } else {
            view?.showSnackbar(Constants.SERVER_ERROR)
        }
    }

}