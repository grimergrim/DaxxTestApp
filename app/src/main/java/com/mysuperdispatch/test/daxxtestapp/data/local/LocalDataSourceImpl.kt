package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostDao
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.util.PrefsUtils
import io.reactivex.Flowable
import io.reactivex.Single
import kotlin.concurrent.fixedRateTimer

class LocalDataSourceImpl(private val postDao: PostDao,
                          private val prefsUtils: PrefsUtils) : LocalDataSource {

    private var fixedRateTimer = fixedRateTimer(NAME, false, 0.toLong(), INTERVAL) { }

    override fun getNewPostsCount(lastShownIndex: Long): Flowable<Long> {
        return postDao.getNewPostsCount(lastShownIndex)
    }

    override fun getNewPosts(lastShownIndex: Long): Single<List<Post>> {
        return postDao.getNewPosts(lastShownIndex)
    }

    private fun getNumberOfPosts(): Long {
        return postDao.getMaxIndex() + 1
    }

    override fun getPostsPerPage(lastSmallestShownIndex: Long): Single<List<Post>> {
        return postDao.getPostsPerPage(lastSmallestShownIndex)
    }

    override fun getPostsRefresh(lastShownIndex: Long): Single<List<Post>> {
        return postDao.getPostsRefresh()
    }

    override fun deletePosts() {
        Thread(Runnable {
            postDao.deleteAllPosts()
        }).start()
    }

    override fun stopPostGeneration() {
        fixedRateTimer.cancel()
    }

    override fun startPostGeneration() {
        fixedRateTimer.cancel()
        fixedRateTimer = fixedRateTimer(NAME, false, 0.toLong(), INTERVAL) {
            if (prefsUtils.getFirstStart()) {
                for (i in 1..POSTS_AMOUNT) {
                    postDao.insertPost(Post(TITLE + i, AUTHOR + i,
                            System.currentTimeMillis(), i.toLong()))
                }
                prefsUtils.saveFirstStart()
            }
            generatePost(getNumberOfPosts())
        }
    }

    private fun generatePost(i: Long) {
        for (y in i..i + POSTS_PER_SECOND) {
            postDao.insertPost(Post(TITLE + y, AUTHOR + y, System.currentTimeMillis(), y))
        }
    }

    companion object {
        private const val POSTS_PER_SECOND = 4 //5 posts will be generated
        private const val POSTS_AMOUNT = 100
        private const val INTERVAL = 1000L //1 sec
        private const val NAME = "PostsGenerationTimer"
        private const val TITLE = "Title"
        private const val AUTHOR = "Author"
    }

}