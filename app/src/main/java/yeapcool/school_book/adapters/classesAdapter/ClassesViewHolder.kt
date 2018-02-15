package yeapcool.school_book.adapters.classesAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import yeapcool.school_book.R
import yeapcool.school_book.model.data.Link


class ClassesViewHolder(val view: View, val listener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(view) {

    private val tv = itemView.findViewById<TextView>(R.id.rowClass_tv_class)

    fun bind(item: Link?, position: Int) {
        val n = item?.schoolClass?.number
        val l = item?.schoolClass?.letter
        val s = item?.subject

        if (n != null && l != null && s != null && !s.isEmpty()) {
            val text = n.toString() + " '" + l + "'" + " - " + s
            tv.text = text
        }

        listener?.let {
            itemView.setOnClickListener { listener.invoke(position) }
        }
    }
}