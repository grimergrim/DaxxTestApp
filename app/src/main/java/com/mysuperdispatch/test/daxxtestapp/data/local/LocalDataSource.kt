package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDataSource {

    fun startPostGeneration()

    fun stopPostGeneration()

    fun getPostsPerPage(lastSmallestShownIndex: Long): Single<List<Post>>

    fun getPostsRefresh(lastShownIndex: Long): Single<List<Post>>

    fun deletePosts()

    fun getNewPostsCount(lastShownIndex: Long): Flowable<Long>

    fun getNewPosts(lastShownIndex: Long): Single<List<Post>>

}