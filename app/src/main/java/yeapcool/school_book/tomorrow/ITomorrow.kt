package yeapcool.school_book.tomorrow

import yeapcool.school_book.adapters.newsAdapter.INewsAdapter
import yeapcool.school_book.common.CommonPresenter
import yeapcool.school_book.common.NetworkView


interface ITomorrow {

    interface View: NetworkView {

        fun visibleCv(visible: Boolean)

        fun visibleRv(visible: Boolean)

        fun setSchedule(times: ArrayList<String?>, content: ArrayList<String?>)

    }

    interface Presenter: CommonPresenter {

        fun bind(view: ITomorrow.View, viewAdapter: INewsAdapter.View, modelAdapter: INewsAdapter.Model)

        fun clickToday()

        fun clickTomorrow()

        fun clearTh()

    }

}