package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable

interface Repository {

    fun populateDb()

    fun savePosts(posts: List<Post>)

    fun getPosts(): Flowable<List<Post>>

    fun deletePosts()

}