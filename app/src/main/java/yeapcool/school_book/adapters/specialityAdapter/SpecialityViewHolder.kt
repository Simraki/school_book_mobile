package yeapcool.school_book.adapters.specialityAdapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import yeapcool.school_book.R


class SpecialityViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val name = itemView.findViewById<TextView>(R.id.rowSpeciality_tv_subject)
    private val cb = itemView.findViewById<CheckBox>(R.id.rowSpeciality_cb_included)

    fun bind(item: String?, checked: Boolean = false) {

        name.text = item
        cb.isChecked = checked
        cb.isClickable = false

    }

    fun setListenerItemView(listener: ((Boolean) -> Unit)?) {
        itemView.setOnClickListener {
            listener?.invoke(cb.isChecked)
            cb.toggle()
        }
    }

}