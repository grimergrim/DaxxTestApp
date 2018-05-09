package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Observable

interface Repository {

    fun populateDb()

    fun savePosts(posts: List<Post>)

//    fun getPostsLocal(): Observable<List<Post>>

}