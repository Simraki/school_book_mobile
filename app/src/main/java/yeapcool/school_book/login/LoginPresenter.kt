package yeapcool.school_book.login

import io.reactivex.disposables.CompositeDisposable
import yeapcool.school_book.Constants
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.network.ServerRequest
import yeapcool.school_book.model.network.pojo.User


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
        else {
            // Показать 'ошибку на сервере' на телефоне
        }

    }

    private fun login(email: String, password: String) {
        val user = User(email = email, password = password)
        val request = ServerRequest(operation = Constants.OPERATION_LOGIN, user = user)
        val th = model.network(request)

        if (th != null) {
            val disp_login = th
                    .subscribe({ it ->
                        if (it.result == Constants.SUCCESS)
                            view?.showSnackbar("Good")
                        else
                            view?.showSnackbar("Bad")
                    }, {
                        view?.showSnackbar("Error")
                    })

            disposables.add(disp_login)
        }
    }

    override fun toRegister() {
        view?.toRegister()
    }

    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }
}