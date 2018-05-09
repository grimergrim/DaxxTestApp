package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post

interface LocalDataSource {

    fun savePost(post: Post)

    fun generatePosts()

//    fun getPostsLocal(): Observable<List<Post>>

//    fun getUserByIdLocal(userId: Int): Observable<User>

}