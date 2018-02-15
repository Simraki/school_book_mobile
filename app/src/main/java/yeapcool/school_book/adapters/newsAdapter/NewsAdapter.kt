package yeapcool.school_book.adapters.newsAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import yeapcool.school_book.Constants
import yeapcool.school_book.R
import yeapcool.school_book.model.data.News


class NewsAdapter(private val context: Context)
    : RecyclerView.Adapter<NewsViewHolder>(), INewsAdapter.View, INewsAdapter.Model {

    private lateinit var items: ArrayList<News>

    private val unfolded = ArrayList<Boolean>()

    override fun onBindViewHolder(holder: NewsViewHolder?, position: Int) {
        items[position].let {
            holder?.bind(it, unfolded, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun setItems(items: ArrayList<News>) {
        this.items = items

        for (i in 1..10) {
            unfolded.add(false)
        }
    }

    override fun addItems(items: ArrayList<News>) {
        this.items.addAll(items)
        for (i in 1..10) {
            unfolded.add(false)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItem(position: Int): News? = items[position]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun notifySomeItems(updateSize: Int) {
        notifyItemInserted(updateSize)
    }

}