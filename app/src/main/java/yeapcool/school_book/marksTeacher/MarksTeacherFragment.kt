package yeapcool.school_book.marksTeacher

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_marks_teacher.*
import yeapcool.school_book.R
import yeapcool.school_book.adapters.marksStudentAdapter.ClassesAdapter
import yeapcool.school_book.adapters.marksTeacherAdapter.MarksTeacherAdapter


class MarksTeacherFragment : Fragment(), IMarksTeacher.View, TextWatcher {

    private lateinit var presenter: MarksTeacherPresenter
    private lateinit var adapterClasses: ClassesAdapter
    private lateinit var adapterStudents: MarksTeacherAdapter

    private var initRvClasses = false
    private var initRvStudents = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_marks_teacher, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        presenter = MarksTeacherPresenter()
        adapterClasses = ClassesAdapter(context)
        adapterStudents = MarksTeacherAdapter(context)

        marksTeacher_btn_back.setOnClickListener { presenter.clickBack() }

        marksTeacher_et_description.addTextChangedListener(this)
        marksTeacher_et_description.setOnFocusChangeListener { _, _ ->
            if (!marksTeacher_et_description.text.isEmpty())
                presenter.setDescription(marksTeacher_et_description.text.toString())
        }
    }

    override fun progress(progress: Boolean) {
        marksTeacher_swipeLayout_marksTeacher.isRefreshing = progress
    }

    override fun showSnackbar(message: String) {
        val v = view
        v?.let {
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.bind(this, adapterClasses, adapterClasses, adapterStudents, adapterStudents)
    }

    override fun onPause() {
        super.onPause()

        presenter.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.clearTh()
    }

    override fun setNameClass(nameClass: String) {
        marksTeacher_tv_class.text = nameClass
    }

    override fun visibleRvClasses(visible: Boolean) {
        if (visible) {
            marksTeacher_rv_classes.visibility = View.VISIBLE
            if (!initRvClasses) {
                marksTeacher_rv_classes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                marksTeacher_rv_classes.adapter = adapterClasses
            }
        } else
            marksTeacher_rv_classes.visibility = View.GONE
    }

    override fun visibleStepTwo(visible: Boolean) {
        if (visible)
            marksTeacher_layout_students.visibility = View.VISIBLE
        else
            marksTeacher_layout_students.visibility = View.GONE
    }

    override fun visibleRvStudents(visible: Boolean) {
        if (visible) {
            marksTeacher_rv_students.visibility = View.VISIBLE
            if (!initRvStudents) {
                marksTeacher_rv_students.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                marksTeacher_rv_students.adapter = adapterStudents
            }
        } else
            marksTeacher_rv_students.visibility = View.GONE
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s.toString().let {
            presenter.setDescription(it)
        }
    }



}