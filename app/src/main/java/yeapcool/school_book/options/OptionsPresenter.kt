package yeapcool.school_book.options

import yeapcool.school_book.Constants
import yeapcool.school_book.model.Model


class OptionsPresenter : IOptions.Presenter {

    private var view: IOptions.View? = null
    private val model = Model()

    override fun bind(view: IOptions.View) {
        this.view = view

        setUser()
    }

    private fun setUser() {
        var text = Constants.ERROR
        var type = -1

        model.pref()?.let {
            text = it.getName() + " " + it.getSurname()
            type = it.getTypeUser()
        }

        view?.setUser(text, type)
    }

    override fun unbind() {
        view = null
    }

    override fun clickExit() {
        model.pref()?.let {
            it.clear()
            it.setIsLoggedIn(false)
            view?.finish()
        }
    }
}