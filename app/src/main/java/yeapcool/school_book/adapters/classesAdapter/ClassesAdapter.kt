package yeapcool.school_book.adapters.marksStudentAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import yeapcool.school_book.R
import yeapcool.school_book.adapters.classesAdapter.ClassesViewHolder
import yeapcool.school_book.adapters.classesAdapter.IClassesAdapter
import yeapcool.school_book.model.data.Link


class ClassesAdapter(private val context: Context)
    : RecyclerView.Adapter<ClassesViewHolder>(), IClassesAdapter.View, IClassesAdapter.Model {

    override var onClick: ((Int) -> Unit)? = null

    private lateinit var items: ArrayList<Link>

    override fun onBindViewHolder(holder: ClassesViewHolder?, position: Int) {
        items[position].let {
            holder?.bind(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ClassesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_class, parent, false)
        return ClassesViewHolder(view, onClick)
    }

    override fun setItems(items: ArrayList<Link>) {
        this.items = items
    }

    override fun getItemCount(): Int = items.size

    override fun getItem(position: Int): Link? = items[position]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }
}