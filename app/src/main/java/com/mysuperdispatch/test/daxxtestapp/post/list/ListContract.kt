package com.mysuperdispatch.test.daxxtestapp.post.list

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post

interface ListContract {

    interface ListView {

        fun showPosts(posts: List<Post>)

        fun addNewPosts(posts: List<Post>)

        fun addPostsPerPage(posts: List<Post>)

        fun updateNewPostsCounter(count: Long)

        fun showErrorScreen()

        fun showErrorToast()

    }

    interface ListPresenter {

        fun startPostGeneration()

        fun getPostsPerPage()

        fun getPostsRefresh()

        fun deletePosts()

        fun stopPostGeneration()

        fun getNewPostsCount()

        fun getNewPosts()

        fun onPause()
    }

}