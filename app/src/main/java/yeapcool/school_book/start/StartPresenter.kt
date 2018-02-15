package yeapcool.school_book.start

import yeapcool.school_book.model.Model


class StartPresenter : IStart.Presenter {

    var view: IStart.View? = null
    val model = Model()

    override fun bind(view: IStart.View) {
        this.view = view
    }

    override fun isLoggedIn() {
        val pref = model.pref()

        pref?.let {
            if (pref.getIsLoggedIn()) {
                view?.toMain()
            } else {
                view?.toLogin()
            }
        }
    }

    override fun unbind() {
        view = null
    }
}