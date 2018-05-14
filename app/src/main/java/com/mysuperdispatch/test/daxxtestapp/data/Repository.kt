package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Single

interface Repository {

    fun startPostGeneration()

    fun getPostsPerPage(lastSmallestShownIndex: Long): Single<List<Post>>

    fun getPostsRefresh(lastShownIndex: Long): Single<List<Post>>

    fun deletePosts()

    fun stopPostGeneration()

    fun getNewPostsCount(lastShownIndex: Long): Flowable<Long>

    fun getNewPosts(lastShownIndex: Long): Single<List<Post>>

}