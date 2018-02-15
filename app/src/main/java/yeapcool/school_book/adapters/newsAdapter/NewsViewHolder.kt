package yeapcool.school_book.adapters.newsAdapter

import android.annotation.SuppressLint
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import yeapcool.school_book.Constants
import yeapcool.school_book.R
import yeapcool.school_book.model.data.News
import java.text.SimpleDateFormat


class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val cv = itemView.findViewById<CardView>(R.id.rowNews_cv_layout)
    private val cl = itemView.findViewById<ConstraintLayout>(R.id.rowNews_layout_layout)
    private val title_l = itemView.findViewById<ConstraintLayout>(R.id.rowNews_layout_title)

    private val title = itemView.findViewById<TextView>(R.id.rowNews_tv_title)
    private val dateYear = itemView.findViewById<TextView>(R.id.rowNews_tv_dateYear)
    private val date = itemView.findViewById<TextView>(R.id.rowNews_tv_date)
    private val content = itemView.findViewById<TextView>(R.id.rowNews_tv_content)

    @SuppressLint("SimpleDateFormat")
    fun bind(item: News?, unfolded: ArrayList<Boolean>, position: Int) {
        var t = item?.title
        val d = item?.date
        var c = item?.content

        val context = itemView.context

        if (t == null || t.isEmpty()) {
            t = Constants.ERROR
        }
        if (d == null || d.isEmpty()) {
            dateYear.visibility = View.GONE
            date.visibility = View.GONE
            cl.setPadding(0, 0, 0, 0)
        } else {
            val form = SimpleDateFormat("dd.MM.yy")
            val year = SimpleDateFormat("y").format(form.parse(d))
            val dayMonth = SimpleDateFormat("dd MMMM").format(form.parse(d))

            dateYear.text = year
            date.text = dayMonth
        }
        if (c == null || c.isEmpty()) {
            c = Constants.ERROR
        }

        title.text = t
        content.text = c

        content.visibility = if (unfolded[position]) View.VISIBLE else View.GONE

        itemView.setOnClickListener {
            unfolded[position] = !unfolded[position]

            val expand = content.visibility == View.GONE

            val transition = ChangeBounds()
            transition.duration = 350

            val backgColor = if (expand) ContextCompat.getColor(context, R.color.colorPrimaryDark) else ContextCompat.getColor(context, android.R.color.transparent)
            title_l.setBackgroundColor(backgColor)

            val textTitleColor = if (expand) ContextCompat.getColor(context, android.R.color.white) else ContextCompat.getColor(context, R.color.colorPrimary_text)
            title.setTextColor(textTitleColor)

            content.visibility = if (expand) View.VISIBLE else View.GONE
            if (d != null && !d.isEmpty()) {
                dateYear.visibility = if (expand) View.GONE else View.VISIBLE
                date.visibility = if (expand) View.GONE else View.VISIBLE
            }

            TransitionManager.beginDelayedTransition(itemView as ViewGroup, transition)
            cv.isActivated = expand


        }
    }
}