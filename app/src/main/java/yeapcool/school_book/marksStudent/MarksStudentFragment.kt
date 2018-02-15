package yeapcool.school_book.marksStudent

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_marks_student.*
import yeapcool.school_book.R
import yeapcool.school_book.adapters.marksStudentAdapter.MarksStudentAdapter


class MarksStudentFragment : Fragment(), IMarksStudent.View {

    private lateinit var presenter: MarksStudentPresenter
    private lateinit var adapter: MarksStudentAdapter
    override lateinit var recyclerView: RecyclerView

    private var isInitRv = false
    private var final = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_marks_student, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {

        presenter = MarksStudentPresenter()
        adapter = MarksStudentAdapter(context)

        recyclerView = marksStudent_rv_marks

        val adapterSubject = ArrayAdapter.createFromResource(context, R.array.subject, android.R.layout.simple_spinner_dropdown_item)
        marksStudent_sp_subject.adapter = adapterSubject

        marksStudent_sp_subject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                presenter.setSubject(adapterSubject.getItem(selectedItemPosition).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        marksStudent_btn_finalMarks.setOnClickListener {
            final = !final
            presenter.setFinal(final)
            if (final)
                marksStudent_btn_finalMarks.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            else
                marksStudent_btn_finalMarks.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }

        marksStudent_swipeLayout_marks.setOnRefreshListener { presenter.refresh() }
    }

    override fun onResume() {
        super.onResume()
        presenter.bind(this, adapter, adapter)
    }

    override fun onPause() {
        super.onPause()
        presenter.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearTh()
    }

    override fun showSnackbar(message: String) {
        val v = view
        v?.let {
            Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun progress(progress: Boolean) {
        marksStudent_swipeLayout_marks.isRefreshing = progress
    }

    override fun visibleRv(visible: Boolean) {
        if (visible) {
            marksStudent_tv_NMarks.visibility = View.GONE
            marksStudent_rv_marks.visibility = View.VISIBLE
            if (!isInitRv) {
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
            }
        } else {
            marksStudent_rv_marks.visibility = View.GONE
            marksStudent_tv_NMarks.visibility = View.VISIBLE
        }
    }



}