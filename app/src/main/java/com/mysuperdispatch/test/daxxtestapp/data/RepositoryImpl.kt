package com.mysuperdispatch.test.daxxtestapp.data

import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSource
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Single

class RepositoryImpl(private val mLocalDataSource: LocalDataSource) : Repository {

    override fun deletePosts() {
        mLocalDataSource.deletePosts()
    }

    override fun getPostsPerPage(lastSmallestShownIndex: Long): Single<List<Post>> {
        return mLocalDataSource.getPostsPerPage(lastSmallestShownIndex)
    }

    override fun getPostsRefresh(lastShownIndex: Long): Single<List<Post>> {
        return mLocalDataSource.getPostsRefresh(lastShownIndex)
    }

    override fun startPostGeneration() {
        mLocalDataSource.startPostGeneration()
    }

    override fun stopPostGeneration() {
        mLocalDataSource.stopPostGeneration()
    }

    override fun getNewPostsCount(lastShownIndex: Long): Flowable<Long> {
        return mLocalDataSource.getNewPostsCount(lastShownIndex)
    }

    override fun getNewPosts(lastShownIndex: Long): Single<List<Post>> {
        return mLocalDataSource.getNewPosts(lastShownIndex)
    }

}