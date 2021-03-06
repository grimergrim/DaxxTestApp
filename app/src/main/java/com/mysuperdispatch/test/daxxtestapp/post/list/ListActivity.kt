package com.mysuperdispatch.test.daxxtestapp.post.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.transition.Explode
import android.view.View
import android.view.Window
import android.widget.Toast
import com.mysuperdispatch.test.daxxtestapp.R
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class ListActivity : AppCompatActivity(), ListContract.ListView {

    private lateinit var mListPresenter: ListContract.ListPresenter
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransition()
        setContentView(R.layout.activity_list)
        mListPresenter = ListPresenter(this)
        item_list.addOnScrollListener(recyclerViewOnScrollListener)
        reload_button.setOnClickListener { mListPresenter.getPostsRefresh() }
        swipe_refresh_list.setOnRefreshListener({ mListPresenter.getPostsRefresh() })
        clear_button.setOnClickListener {
            adapter.clear()
            mListPresenter.deletePosts()
        }
        new_posts_counter.setOnClickListener {
            mListPresenter.getNewPosts()
            mListPresenter.getNewPostsCount()
        }
    }

    override fun onResume() {
        super.onResume()
        mListPresenter.startPostGeneration()
        mListPresenter.getNewPostsCount()
    }

    override fun onPause() {
        super.onPause()
        mListPresenter.stopPostGeneration()
    }

    override fun addNewPosts(posts: List<Post>) {
        adapter.addNewPosts(posts)
        item_list.smoothScrollToPosition(0)
        mListPresenter.getNewPostsCount()
    }

    override fun addPostsPerPage(posts: List<Post>) {
        adapter.addPostsPerPage(posts)
    }

    override fun showPosts(posts: List<Post>) {
        error_container.visibility = View.GONE
        frame_layout_list.visibility = View.VISIBLE
        clear_button.visibility = View.VISIBLE
        if (swipe_refresh_list.isRefreshing) swipe_refresh_list.isRefreshing = false
        setupRecyclerView(item_list, posts)
        mListPresenter.getNewPostsCount()
    }

    override fun updateNewPostsCounter(count: Long) {
        if (frame_layout_list.visibility == View.VISIBLE && count > 0) {
            new_posts_counter.visibility = View.VISIBLE
            new_posts_counter.text = String.format(getString(R.string.new_posts), count)
        } else {
            new_posts_counter.visibility = View.GONE
        }

    }

    override fun showErrorScreen() {
        error_container.visibility = View.VISIBLE
        frame_layout_list.visibility = View.GONE
        clear_button.visibility = View.GONE
    }

    override fun showErrorToast() {
        Toast.makeText(applicationContext, getString(R.string.error_text), Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, posts: List<Post>) {
        adapter = ListAdapter(LinkedList(posts))
        recyclerView.adapter = adapter
    }

    private fun setTransition() {
        window.allowEnterTransitionOverlap = true
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Explode()
            exitTransition = Explode()
        }
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!(recyclerView?.canScrollVertically(1))!!
                    && recyclerView.adapter.itemCount >= 10) {
                mListPresenter.getPostsPerPage()
            }
        }
    }

}
