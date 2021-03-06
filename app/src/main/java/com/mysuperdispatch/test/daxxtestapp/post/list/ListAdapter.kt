package com.mysuperdispatch.test.daxxtestapp.post.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mysuperdispatch.test.daxxtestapp.R
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.util.DateUtils
import kotlinx.android.synthetic.main.item_list_content.view.*
import java.util.*

class ListAdapter(private val values: LinkedList<Post>) :
        RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.authorView.text = item.author
        holder.dateView.text = DateUtils.convertToStringDate(item.publishedAt)
    }

    override fun getItemCount() = values.size

    fun addNewPosts(posts: List<Post>) {
        for (item in posts) values.push(item)
        notifyDataSetChanged()
    }

    fun addPostsPerPage(posts: List<Post>) {
        values.addAll(posts)
        notifyDataSetChanged()
    }

    fun clear() {
        values.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.title
        val authorView: TextView = view.author
        val dateView: TextView = view.date
    }
}