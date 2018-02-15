package yeapcool.school_book.adapters.marksStudentAdapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import yeapcool.school_book.Constants
import yeapcool.school_book.R
import yeapcool.school_book.model.data.Mark


class MarksStudentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val date = itemView.findViewById<TextView>(R.id.rowMarksStudent_tv_date)
    private val subject = itemView.findViewById<TextView>(R.id.rowMarksStudent_tv_subject)
    private val mark = itemView.findViewById<TextView>(R.id.rowMarksStudent_tv_mark)

    fun bind(item: Mark?) {

        var d = item?.date
        var s = item?.subject
        var v = item?.value

        if (d == null || d.isEmpty()) {
            d = Constants.ERROR
        }
        if (s == null || s.isEmpty()) {
            s = Constants.ERROR
        }
        if (v == null || v > 5 || v < 1) {
            v = Constants.PREF_number_def
        }

        date.text = d
        subject.text = s
        mark.text = v.toString()

        if (item?.final != null && item.final) {
            mark.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        } else {
            mark.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorMark))
        }

        item?.description?.let {
            itemView.setOnClickListener {
                Toast.makeText(itemView.context, item.description, Toast.LENGTH_SHORT).show()
            }
        }
    }
}