package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Single

interface Repository {

    fun startPostGeneration()

    fun getPostsPerPage(lastSmallestShownDate: Long): Single<List<Post>>

    fun getPostsRefresh(lastShownDate: Long): Single<List<Post>>

    fun deletePosts()

    fun stopPostGeneration()

    fun getNewPostsCount(lastShownDate: Long): Flowable<Long>

    fun getNewPosts(lastShownDate: Long): Single<List<Post>>

}