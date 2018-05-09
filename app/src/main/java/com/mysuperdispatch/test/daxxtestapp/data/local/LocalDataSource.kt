package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Observable

interface LocalDataSource {

    fun savePost(post: Post)

    fun generatePosts()

    fun getPosts(): Flowable<List<Post>>

    fun deletePosts()

//    fun getUserByIdLocal(userId: Int): Observable<User>

}