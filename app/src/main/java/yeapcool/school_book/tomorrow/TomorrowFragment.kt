package yeapcool.school_book.tomorrow

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_tomorrow.*
import yeapcool.school_book.Constants
import yeapcool.school_book.HelpMethods
import yeapcool.school_book.R
import yeapcool.school_book.adapters.newsAdapter.NewsAdapter


class TomorrowFragment : Fragment(), ITomorrow.View {

    private lateinit var presenter: TomorrowPresenter
    private lateinit var adapter: NewsAdapter

    private var initRv = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_tomorrow, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        presenter = TomorrowPresenter()

        adapter = NewsAdapter(context)

        tomorrow_btn_today.setOnClickListener {
            tomorrow_btn_today.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            tomorrow_btn_tomorrow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            presenter.clickToday()
        }

        tomorrow_btn_tomorrow.setOnClickListener {
            tomorrow_btn_tomorrow.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            tomorrow_btn_today.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            presenter.clickTomorrow()
        }

        tomorrow_btn_today.performClick()
    }

    override fun setSchedule(times: ArrayList<String?>, content: ArrayList<String?>) {
        tomorrow_layout_times.removeAllViews()
        tomorrow_layout_content.removeAllViews()

        val px = HelpMethods.dpToPx(context, 4)
        val params = MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        params.topMargin = px
        params.bottomMargin = px

        times.forEach { it ->
            val tv = TextView(context)
            tv.layoutParams = params

            if (it != null)
                tv.text = it
            else
                tv.text = ""

            if (Build.VERSION.SDK_INT < 23)
                tv.setTextAppearance(context, R.style.TimeHeader)
            else
                tv.setTextAppearance(R.style.TimeHeader)

            tomorrow_layout_times.addView(tv)
        }
        content.forEach { it ->
            val tv = TextView(context)
            tv.layoutParams = params

            if (it != null)
                tv.text = it
            else
                tv.text = ""

            if (Build.VERSION.SDK_INT < 23)
                tv.setTextAppearance(context, R.style.LessonHeader)
            else
                tv.setTextAppearance(R.style.LessonHeader)
            tomorrow_layout_content.addView(tv)
        }

        if (content.size > 7 || times.size > 7) {
            tomorrow_cv_schedule.layoutParams.height = HelpMethods.dpToPx(context, 280)
        } else {
            tomorrow_cv_schedule.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
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
        tomorrow_swipeLayout_tomorrow.isRefreshing = progress
    }

    override fun visibleRv(visible: Boolean) {
        if (visible) {
            tomorrow_tv_NHomework.visibility = View.GONE
            tomorrow_rv_homework.visibility = View.VISIBLE

            if (!initRv) {
                tomorrow_rv_homework.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                tomorrow_rv_homework.adapter = adapter
            }
        } else {
            tomorrow_rv_homework.visibility = View.GONE
            tomorrow_tv_NHomework.visibility = View.VISIBLE
        }
    }

    override fun visibleCv(visible: Boolean) {
        if (visible) {
            tomorrow_tv_NSchedule.visibility = View.GONE
            tomorrow_cv_schedule.visibility = View.VISIBLE
        } else {
            tomorrow_cv_schedule.visibility = View.GONE
            tomorrow_tv_NSchedule.visibility = View.VISIBLE
        }
    }

}