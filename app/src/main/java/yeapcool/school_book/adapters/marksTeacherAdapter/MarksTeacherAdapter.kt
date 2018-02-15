package yeapcool.school_book.adapters.marksTeacherAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import yeapcool.school_book.Constants
import yeapcool.school_book.R
import yeapcool.school_book.model.data.User


class MarksTeacherAdapter(private val context: Context)
    : RecyclerView.Adapter<MarksTeacherViewHolder>(), IMarksTeacherAdapter.View, IMarksTeacherAdapter.Model {

    private var items = ArrayList<User>()
    private var unfolded = ArrayList<Boolean>()

    override var onClickVisit: ((Int) -> Unit)? = null
    override var onClickTwo: ((Int) -> Unit)? = null
    override var onClickThree: ((Int) -> Unit)? = null
    override var onClickFour: ((Int) -> Unit)? = null
    override var onClickFive: ((Int) -> Unit)? = null
    override var onClickDelete: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MarksTeacherViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_marks_teacher, parent, false)
        return MarksTeacherViewHolder(view, onClickVisit, onClickTwo, onClickThree, onClickFour, onClickFive, onClickDelete)
    }

    override fun onBindViewHolder(holder: MarksTeacherViewHolder?, position: Int) {
        items[position].let {
            holder?.bind(it, unfolded, position)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItem(position: Int): User = items[position]

    override fun setItems(items: ArrayList<User>) {
        this.items = items

        unfolded = ArrayList()
        for (i in 1..items.size) {
            unfolded.add(false)
        }
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addMarksVisit(markOrVisit: String, position: Int) {
        Log.i(Constants.TAG, "21121212")
        Log.i(Constants.TAG, getItem(position).toString())
        val list = getItem(position).marksVisit
        if (list == null || list.isEmpty())
            getItem(position).marksVisit = ArrayList()
        if (list?.size == 4)
            list.removeAt(0)
        getItem(position).marksVisit?.add(markOrVisit)
        Log.i(Constants.TAG, getItem(position).toString())
        notifyItemRangeChanged(position, 1)
        notifyItemInserted(position)
        notifyItemChanged(position)
        notifyAdapter()
    }


}