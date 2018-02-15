package yeapcool.school_book.main

import yeapcool.school_book.common.CommonPresenter


interface IMain {

    interface View {

    }

    interface Presenter: CommonPresenter {

        fun bind (view: IMain.View)

        fun getType(): Int?

    }

}