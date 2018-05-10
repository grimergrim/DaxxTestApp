package com.mysuperdispatch.test.daxxtestapp.post.list

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post

interface ListContract {

    interface ListView {

        fun showPosts(posts: List<Post>)

    }

    interface ListPresenter {

        fun startPostGeneration()

        fun getPosts()

        fun deletePosts()

        fun stopPostGeneration()

    }

}