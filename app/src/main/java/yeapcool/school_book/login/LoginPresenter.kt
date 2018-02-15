package yeapcool.school_book.login

import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.data.User
import yeapcool.school_book.model.network.ServerRequest


class LoginPresenter : ILogin.Presenter {

    private var view: ILogin.View? = null
    private val model = Model()
    private val disposables = CompositeDisposable()

    override fun bind(view: ILogin.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun clickBtnLogin(email: String, password: String) {

        if (email.isEmpty())
            view?.errorEmail(Constants.ERROR_EMPTY)
        else
            view?.errorEmail(Constants.NO_ERROR)

        if (password.isEmpty())
            view?.errorPassword(Constants.ERROR_EMPTY)
        else
            view?.errorPassword(Constants.NO_ERROR)

        if (!email.isEmpty() && !password.isEmpty())
            login(email, password)

    }

    private fun login(email: String, password: String) {
        val user = User(email = email, password = password)
        val request = ServerRequest(operation = Constants.OPERATION_LOGIN, user = user)
        val th = model.network(request)


        if (th != null) {

            val disp_login = th
                    .subscribe({ it ->
                        if (it.result == Constants.SUCCESS)
                            if (it.user != null) {

                                it.user?.let { loginSubscriberSuccess(it) }

                                view?.toMain()
                            } else {
                                view?.showSnackbar(Constants.SERVER_ERROR)
                            }
                        else {
                            view?.showSnackbar(HelpMethods.responseError(it))
                        }
                    }, {
                        view?.showSnackbar(Constants.SERVER_ERROR)
                    })

            disposables.add(disp_login)
        }
    }

    private fun loginSubscriberSuccess(user: User) {
        model.pref()?.setName(user.name)
        model.pref()?.setSurname(user.surname)
        model.pref()?.setUn_id(user.un_id)

        user.type?.let {
            model.pref()?.setTypeUser(it)
            if (it == 1 ) {
                val schoolClass = user.classStudent

                Log.i(Constants.TAG, schoolClass.toString())

                schoolClass?.id_class?.let { model.pref()?.setId_class(it) }
                schoolClass?.number?.let { model.pref()?.setClassNumber(it) }
                schoolClass?.letter?.let { model.pref()?.setClassLetter(it) }
            }
        }

        user.id_user?.let { model.pref()?.setId_user(it) }
        model.pref()?.setIsLoggedIn(true)
    }

    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }
}