package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSource
import io.reactivex.Flowable
import io.reactivex.Observable

class RepositoryImpl(private val mLocalDataSource: LocalDataSource) : Repository {

    override fun savePosts(posts: List<Post>) {
//        mLocalDataSource.savePosts(posts)
    }

    override fun getPosts(): Flowable<List<Post>> {
        return mLocalDataSource.getPosts()
    }

    override fun populateDb() {
            mLocalDataSource.generatePosts()
    }

}