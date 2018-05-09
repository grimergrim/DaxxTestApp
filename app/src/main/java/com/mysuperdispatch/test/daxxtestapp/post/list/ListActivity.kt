package com.mysuperdispatch.test.daxxtestapp.post.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.transition.Explode
import android.view.View
import android.view.Window
import com.mysuperdispatch.test.daxxtestapp.R
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.toolbar.*


class ListActivity : AppCompatActivity(), ListContract.ListView {

    private lateinit var mListPresenter: ListContract.ListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransition()
        setContentView(R.layout.activity_list)

        reload_button.setOnClickListener { mListPresenter.getPosts() }
        item_list.addOnScrollListener(recyclerViewOnScrollListener)
        clear_button.setOnClickListener { mListPresenter.deletePosts() }
//        clear_button.setOnClickListener { mListPresenter.deleteAllPosts() }

//        TODO("get posts, hide error, show posts")
        mListPresenter = ListPresenter(this)
//        showPosts(listOf(
//                Post("1", "1", System.currentTimeMillis()),
//                Post("1", "1", System.currentTimeMillis())))
        swipe_refresh_list.setOnRefreshListener({ refresh() })
//        item_list.
        mListPresenter.launch()
    }

    override fun showPosts(posts: List<Post>) {
        error_container.visibility = View.GONE
        frame_layout_list.visibility = View.VISIBLE
        if (swipe_refresh_list.isRefreshing) swipe_refresh_list.isRefreshing = false
        setupRecyclerView(item_list, posts)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, posts: List<Post>) {
        recyclerView.adapter = ListAdapter(posts)
    }

    private fun refresh() {
        mListPresenter.getPosts()
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
//            val visibleItemCount = layoutManager.getChildCount()
//            val totalItemCount = layoutManager.getItemCount()
//            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//
//            if (!isLoading && !isLastPage) {
//                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
//                        && firstVisibleItemPosition >= 0
//                        && totalItemCount >= PAGE_SIZE) {
//                    loadMoreItems()
//                }
//            }
        }
    }

}
