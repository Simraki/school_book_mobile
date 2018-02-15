package yeapcool.school_book.tomorrow

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.adapters.newsAdapter.INewsAdapter
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.data.Homework
import yeapcool.school_book.model.data.News
import yeapcool.school_book.model.data.Schedule
import yeapcool.school_book.model.data.User
import yeapcool.school_book.model.network.ServerRequest
import java.text.SimpleDateFormat
import java.util.*


class TomorrowPresenter : ITomorrow.Presenter {

    private var view: ITomorrow.View? = null
    private val model = Model()

    private var viewAdapter: INewsAdapter.View? = null
    private var modelAdapter: INewsAdapter.Model? = null

    private val disposables = CompositeDisposable()

    private var calendar: Calendar? = null
    private var tomorrow = false

    override fun bind(view: ITomorrow.View, viewAdapter: INewsAdapter.View, modelAdapter: INewsAdapter.Model) {
        this.view = view
        this.viewAdapter = viewAdapter
        this.modelAdapter = modelAdapter
    }

    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }

    override fun unbind() {
        calendar = null
        viewAdapter = null
        modelAdapter = null
        view = null
    }

    @SuppressLint("SimpleDateFormat")
    private fun getSchedule() {

        val id_user = model.pref()?.getId_user()
        val type = model.pref()?.getTypeUser()

        if (id_user != null && id_user != Constants.PREF_number_def
                && type != null && type != Constants.PREF_number_def) {

            calendar = Calendar.getInstance()
            val form = SimpleDateFormat("dd.MM")
            val date_today = form.format(calendar?.time)

            var date = date_today
            if (tomorrow) {

                if (calendar?.get(Calendar.DAY_OF_WEEK) == 7)
                    calendar?.add(Calendar.DAY_OF_MONTH, 2)
                else
                    calendar?.add(Calendar.DAY_OF_MONTH, 1)

                date = form.format(calendar?.time)
            }

            view?.progress(true)

            val schedule = Schedule(date = date)
            if (type == 1) {
                val classNumber = model.pref()?.getClassNumber()
                if (classNumber != null && classNumber != Constants.PREF_number_def)
                    schedule.highClass = classNumber >= 9
            }

            val user = User(id_user = id_user)
            val request = ServerRequest(operation = Constants.OPERATION_GET_SCHEDULE, user = user, schedule = schedule)
            val th = model.network(request)

            if (th != null) {
                val times = ArrayList<String?>()
                val content = ArrayList<String?>()

                val disp_getSchedule = th
                        .doOnTerminate { terminateGetSchedule(times, content) }
                        .subscribe { it ->
                            if (it.result == Constants.SUCCESS) {
                                it.schedule?.times?.let { times.addAll(it) }
                                it.schedule?.content?.let { content.addAll(it) }
                            } else {
                                //view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }

                disposables.add(disp_getSchedule)

                getHomework(id_user, date)
            }
        }
    }

    private fun terminateGetSchedule(times: ArrayList<String?>, content: ArrayList<String?>) {

        if (!times.isEmpty() && !content.isEmpty()) {
            view?.setSchedule(times, content)
            view?.visibleCv(true)
        } else {
            view?.visibleCv(false)
        }

        view?.progress(false)
    }

    private fun getHomework(id_user: Int, date: String) {

        view?.progress(true)

        val user = User(id_user = id_user)
        val homework = Homework(date = date)
        val request = ServerRequest(operation = Constants.OPERATION_GET_HOMEWORK, user = user, homework = homework)
        val th = model.network(request)

        if (th != null) {
            val list = ArrayList<News>()

            val disp_getHomework = th
                    .doOnTerminate { terminateGetHomework(list) }
                    .subscribe { it ->
                        if (it.result == Constants.SUCCESS) {
                            it.homework?.let { list.addAll(it) }
                        } else {
                            //view?.showSnackbar(HelpMethods.responseError(it))
                        }
                    }

            disposables.add(disp_getHomework)
        }
    }

    private fun terminateGetHomework(list: ArrayList<News>) {

        if (!list.isEmpty()) {
            modelAdapter?.setItems(list)
            modelAdapter?.notifyAdapter()
            view?.visibleRv(true)
        } else {
            view?.visibleRv(false)
        }

        view?.progress(false)
    }

    override fun clickToday() {
        tomorrow = false
        getSchedule()
    }

    override fun clickTomorrow() {
        tomorrow = true
        getSchedule()
    }
}