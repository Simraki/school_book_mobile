package yeapcool.school_book.main

import yeapcool.school_book.model.Model


class MainPresenter: IMain.Presenter {

    private var view: IMain.View? = null
    private val model = Model()

    override fun bind(view: IMain.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun getType(): Int? = model.pref()?.getTypeUser()

}