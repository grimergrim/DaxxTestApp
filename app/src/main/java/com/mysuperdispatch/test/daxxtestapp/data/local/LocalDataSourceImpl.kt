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

    override fun getNewPostsCount(lastShownDate: Long): Flowable<Long> {
        return postDao.getNewPostsCount(lastShownDate)
    }

    override fun getNewPosts(lastShownDate: Long): Single<List<Post>> {
        return postDao.getNewPosts(lastShownDate)
    }

    private fun getNumberOfPosts(): Long {
        return postDao.getMaxIndex() + 1
    }

    override fun getPostsPerPage(lastSmallestShownDate: Long): Single<List<Post>> {
        return postDao.getPostsPerPage(lastSmallestShownDate)
    }

    override fun getPostsRefresh(lastShownDate: Long): Single<List<Post>> {
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
        Thread(Runnable {
            if (prefsUtils.getFirstStart()) {
                for (i in 1..POSTS_AMOUNT) {
                    postDao.insertPost(Post(TITLE + i, AUTHOR + i,
                            System.currentTimeMillis(), i.toLong()))
                }
                prefsUtils.saveFirstStart()
            }
        }).start()
        fixedRateTimer = fixedRateTimer(NAME, false, 0.toLong(), INTERVAL) {
            generatePost(getNumberOfPosts())
        }
    }

    private fun generatePost(i: Long) {
        postDao.insertPost(Post(TITLE + i, AUTHOR + i, System.currentTimeMillis(), i))
    }

    companion object {
        private const val POSTS_AMOUNT = 100
        private const val INTERVAL = 1000L //1 sec
        private const val NAME = "PostsGenerationTimer"
        private const val TITLE = "Title"
        private const val AUTHOR = "Author"
    }

}