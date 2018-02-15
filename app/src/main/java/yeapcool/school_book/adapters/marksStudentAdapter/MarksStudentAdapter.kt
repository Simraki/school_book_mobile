package yeapcool.school_book.adapters.marksStudentAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import yeapcool.school_book.R
import yeapcool.school_book.model.data.Mark


class MarksStudentAdapter(private val context: Context)
    : RecyclerView.Adapter<MarksStudentViewHolder>(), IMarksStudentAdapter.View, IMarksStudentAdapter.Model {


    private lateinit var items: ArrayList<Mark>

    override fun onBindViewHolder(holder: MarksStudentViewHolder?, position: Int) {
        items[position].let {
            holder?.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MarksStudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_marks_student, parent, false)
        return MarksStudentViewHolder(view)
    }

    override fun setItems(items: ArrayList<Mark>) {
        this.items = items
    }

    override fun addItems(items: ArrayList<Mark>) {
        this.items.addAll(items)
    }

    override fun getItemCount(): Int = items.size

    override fun getItem(position: Int): Mark? = items[position]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun notifySomeItems(updateSize: Int) {
        notifyItemInserted(updateSize)
    }
}