package com.mysuperdispatch.test.daxxtestapp.post.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.transition.Explode
import android.view.Window
import com.mysuperdispatch.test.daxxtestapp.R

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.item_list.*

class ListActivity : AppCompatActivity(), ListContract.ListView {

    private lateinit var mListPresenter: ListContract.ListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransition()
        setContentView(R.layout.activity_list)
        mListPresenter = ListPresenter(this)
        showPosts(listOf(
                Post("1", "1", System.currentTimeMillis()),
                Post("1", "1", System.currentTimeMillis())))
        swipe_refresh_list.setOnRefreshListener({ refresh() })
        mListPresenter.launch()
    }

    private fun showPosts(posts: List<Post>) {
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

}
