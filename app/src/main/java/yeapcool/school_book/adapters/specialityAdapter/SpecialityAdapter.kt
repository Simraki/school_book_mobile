package yeapcool.school_book.adapters.specialityAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import yeapcool.school_book.R


class SpecialityAdapter(private val context: Context, private val items: ArrayList<String>)
    : RecyclerView.Adapter<SpecialityViewHolder>(), ISpecialityAdapter.View, ISpecialityAdapter.Model {

    private val list = ArrayList<String>()

    private var checked = Array(items.size, {false})

    override fun onBindViewHolder(holder: SpecialityViewHolder?, position: Int) {
        holder?.bind(items[position], checked[position])
        holder?.setListenerItemView {
            if (!it)
                list.add(items[position])
            else
                list.remove(items[position])
            checked[position] = !checked[position]
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SpecialityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_speciality, parent, false)
        return SpecialityViewHolder(view)
    }

    override fun getItem(position: Int): String? = items[position]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    fun getList() = list
}