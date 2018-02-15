package yeapcool.school_book.marksTeacher

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.adapters.classesAdapter.IClassesAdapter
import yeapcool.school_book.adapters.marksTeacherAdapter.IMarksTeacherAdapter
import yeapcool.school_book.model.Model
import yeapcool.school_book.model.data.*
import yeapcool.school_book.model.network.ServerRequest
import java.util.*


class MarksTeacherPresenter : IMarksTeacher.Presenter {

    private var view: IMarksTeacher.View? = null
    private val model = Model()
    private var viewAdapterClasses: IClassesAdapter.View? = null
    private var modelAdapterClasses: IClassesAdapter.Model? = null
        set(value) {
            field = value
            field?.onClick = { clickItemClass(it) }
        }
    private var viewAdapterStudents: IMarksTeacherAdapter.View? = null
    private var modelAdapterStudents: IMarksTeacherAdapter.Model? = null
        set(value) {
            field = value
            field?.onClickVisit = { clickVisit(it) }
            field?.onClickTwo = { clickMarkTwo(it) }
            field?.onClickThree = { clickMarkThree(it) }
            field?.onClickFour = { clickMarkFour(it) }
            field?.onClickFive = { clickMarkFive(it) }
            field?.onClickDelete = { clickDeleteMark(it) }
        }

    private val disposables = CompositeDisposable()

    private var subject = ""
    private var description = ""


    override fun bind(view: IMarksTeacher.View, viewAdapterClasses: IClassesAdapter.View, modelAdapterClasses: IClassesAdapter.Model,
                      viewAdapterStudents: IMarksTeacherAdapter.View, modelAdapterStudents: IMarksTeacherAdapter.Model) {
        this.view = view
        this.viewAdapterClasses = viewAdapterClasses
        this.modelAdapterClasses = modelAdapterClasses
        this.viewAdapterStudents = viewAdapterStudents
        this.modelAdapterStudents = modelAdapterStudents

        getClasses()
    }

    override fun setDescription(description: String) {
        this.description = description
    }

    override fun unbind() {
        view = null
        viewAdapterClasses = null
        modelAdapterClasses = null
    }

    override fun clearTh() {
        disposables.clear()
        disposables.dispose()
    }

    private fun getClasses() {

        val id_user = model.pref()?.getId_user()

        if (id_user != -1) {

            view?.progress(true)

            val user = User(id_user = id_user)
            val request = ServerRequest(operation = Constants.OPERATION_GET_CLASSES_TEACHER, user = user)
            val th = model.network(request)

            if (th != null) {

                val list = ArrayList<Link>()

                val disp_getClasses = th
                        .doOnTerminate { terminateGetClasses(list) }
                        .subscribe { it ->
                            val l = it.links
                            if (it.result == Constants.SUCCESS && l != null && !l.isEmpty()) {
                                list.addAll(l)
                            } else {
                                view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }

                disposables.add(disp_getClasses)
            }
        }
    }

    private fun terminateGetClasses(list: ArrayList<Link>) {
        view?.visibleStepTwo(false)
        if (list.isEmpty()) {
            view?.visibleRvClasses(false)
        } else {
            modelAdapterClasses?.setItems(list)
            modelAdapterClasses?.notifyAdapter()
            view?.visibleRvClasses(true)
        }

        view?.progress(false)
    }

    override fun clickBack() {
        view?.visibleRvClasses(true)
        view?.visibleStepTwo(false)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getStudents(id_class: Int, nameClass: String, subject: String) {

        view?.progress(true)

        val getterStudents = GetterStudents(id_class = id_class, getMarksVisit =  true, subject = subject)
        val request = ServerRequest(operation = Constants.OPERATION_GET_STUDENTS, getterStudents = getterStudents)
        val th = model.network(request)

        if (th != null) {

            val list = ArrayList<User>()

            val disp_getStudents = th
                    .doOnTerminate { terminateGetStudents(list, nameClass, subject) }
                    .subscribe { it ->
                        val u = it.users
                        if (it.result == Constants.SUCCESS && u != null && !u.isEmpty()) {
                            list.addAll(u)
                        } else {
                            Log.i(Constants.TAG, "->" + it.toString())
                            view?.showSnackbar(HelpMethods.responseError(it))
                        }
                    }

            disposables.add(disp_getStudents)
        }
    }

    private fun terminateGetStudents(list: ArrayList<User>, nameClass: String, subject: String) {
        view?.visibleRvClasses(false)
        view?.visibleStepTwo(true)
        if (list.isEmpty()) {
            view?.visibleRvStudents(false)
        } else {
            modelAdapterStudents?.setItems(list)
            modelAdapterStudents?.notifyAdapter()
            view?.visibleRvStudents(true)
            view?.setNameClass(nameClass)
        }

        if (this.subject != subject) {
            this.subject = subject
        }

        view?.progress(false)
    }

    private fun clickItemClass(position: Int) {
        val id = viewAdapterClasses?.getItem(position)?.schoolClass?.id_class
        val n = viewAdapterClasses?.getItem(position)?.schoolClass?.number
        val l = viewAdapterClasses?.getItem(position)?.schoolClass?.letter
        val s = viewAdapterClasses?.getItem(position)?.subject
        if (n != null && l != null && s != null && !s.isEmpty() && id != null && id != -1) {
            val nameClass = n.toString() + " '" + l + "'" + " - " + s
            getStudents(id, nameClass, s)
        }
    }

    private fun clickVisit(position: Int) {

        val id = viewAdapterStudents?.getItem(position)?.id_user

        if (id != null && id != -1) {
            val visit = Visit(id_student = id, subject = subject)
            val request = ServerRequest(operation = Constants.OPERATION_SET_VISIT, visit = visit)
            val th = model.network(request)

            if (th != null) {

                val disp_setVisit = th
                        .subscribe { it ->
                            if (it.result == Constants.SUCCESS) {
                                addMarksVisit("Н", position)
                            } else {
                                view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }

                disposables.add(disp_setVisit)
            }
        }
    }

    private fun clickMarkTwo(position: Int) {
        setMark(position, 2)
    }

    private fun clickMarkThree(position: Int) {
        setMark(position, 3)
    }

    private fun clickMarkFour(position: Int) {
        setMark(position, 4)
    }

    private fun clickMarkFive(position: Int) {
        setMark(position, 5)
    }

    private fun clickDeleteMark(position: Int) {

        Log.i(Constants.TAG, "Delete")

    }

    private fun setMark(position: Int, value: Int) {
        val id = viewAdapterStudents?.getItem(position)?.id_user

        if (id != null && id != -1) {
            val mark = Mark(student = id.toString(), subject = subject, value = value, description = description)
            val request = ServerRequest(operation = Constants.OPERATION_SET_MARK, mark = mark)
            val th = model.network(request)

            if (th != null) {

                val disp_setMark = th.doOnTerminate { }
                        .subscribe { it ->
                            if (it.result == Constants.SUCCESS) {
                                addMarksVisit(value.toString(), position)
                            } else {
                                view?.showSnackbar(HelpMethods.responseError(it))
                            }
                        }

                disposables.add(disp_setMark)
            }
        }
    }

    private fun addMarksVisit(markOrVisit: String, position: Int) {
        modelAdapterStudents?.addMarksVisit(markOrVisit, position)
    }
}