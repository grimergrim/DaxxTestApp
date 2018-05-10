package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class RepositoryImpl(private val mLocalDataSource: LocalDataSource) : Repository {

    override fun savePosts(posts: List<Post>) {
//        mLocalDataSource.savePosts(posts)
    }

    override fun deletePosts() {
        mLocalDataSource.deletePosts()
    }

    override fun getPosts(): Single<List<Post>> {
        return mLocalDataSource.getPosts()
    }

    override fun startPostGeneration() {
            mLocalDataSource.startPostGeneration()
    }

    override fun stopPostGeneration() {
        mLocalDataSource.stopPostGeneration()
    }

    override fun getNewPostsCount(lastShownDate: Long): Flowable<Long> {
        return mLocalDataSource.getNewPostsCount(lastShownDate)
    }

    override fun getNewPosts(lastShownDate: Long): Single<List<Post>> {
        return mLocalDataSource.getNewPosts(lastShownDate)
    }

}