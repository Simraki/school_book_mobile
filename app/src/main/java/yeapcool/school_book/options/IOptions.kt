package yeapcool.school_book.options

import android.widget.ImageView
import yeapcool.school_book.common.CommonPresenter


interface IOptions {

    interface View {

        var imageUser: ImageView

        fun setUser(name: String, type: Int)

        fun finish()

    }

    interface Presenter: CommonPresenter {

        fun bind(view: IOptions.View)

        fun clickExit()

    }
}