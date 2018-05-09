package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSource

class RepositoryImpl(private val mLocalDataSource: LocalDataSource) : Repository {

    override fun savePosts(posts: List<Post>) {
//        mLocalDataSource.savePosts(posts)
    }

//    override fun getPostsLocal(): Observable<List<Post>> {
//        return mLocalDataSource.getPostsLocal()
//    }

    override fun populateDb() {
            mLocalDataSource.generatePosts()
    }

}