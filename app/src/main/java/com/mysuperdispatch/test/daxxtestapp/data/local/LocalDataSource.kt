package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalDataSource {

    fun startPostGeneration()

    fun stopPostGeneration()

    fun getPosts(): Single<List<Post>>

    fun deletePosts()

    fun getNewPostsCount(lastShownDate: Long): Flowable<Long>

//    fun getUserByIdLocal(userId: Int): Observable<User>

}