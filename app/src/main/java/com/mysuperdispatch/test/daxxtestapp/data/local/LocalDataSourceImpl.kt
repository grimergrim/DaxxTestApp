package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostDao
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.util.PrefsUtils
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSourceImpl(private val postDao: PostDao,
                          private val prefsUtils: PrefsUtils) : LocalDataSource {

    private var generate: Boolean = false

    override fun startPostGeneration() {
        Thread(Runnable {
            if (prefsUtils.getFirstStart()) {
                for (i in 1..POSTS_AMOUNT) {
                    postDao.insertPost(Post("Title" + i, "Author" + i,
                            System.currentTimeMillis(), i.toLong()))
                }
            prefsUtils.saveFirstStart()
            }
            generate = true
            generatePost(getNumberOfPosts())
        }).start()
    }

    override fun stopPostGeneration() {
        generate = false
    }

    override fun getNewPostsCount(lastShownDate: Long): Flowable<Long> {
        return postDao.getNewPostsCount(lastShownDate)
    }

    override fun getNewPosts(lastShownDate: Long): Single<List<Post>> {
        return postDao.getNewPosts(lastShownDate)
    }

    private fun generatePost(i: Long) {
        var counter: Long = i
        while (generate) {
            Thread.sleep(GENERATION_INTERVAL)
            postDao.insertPost(Post("Title" + counter,"Author" + counter,
                    System.currentTimeMillis(), counter))
            counter++
        }
    }

    private fun getNumberOfPosts(): Long {
        return postDao.getMaxIndex() + 1
    }

    override fun getPosts(): Single<List<Post>> {
        return postDao.getAllPosts()
    }

    override fun deletePosts() {
        Thread(Runnable {
            postDao.deleteAllPosts()
        }).start()
    }

//    override fun saveUsers(users: List<User>) {
//        usersCache = users
//    }
//
//    override fun getPostsLocal(): Observable<List<Post>> {
//        return Observable.just(postsCache)
//    }
//
//    override fun getUserByIdLocal(userId: Int): Observable<User> {
//        val selectedUser: User = usersCache.single { user -> user.id == userId }
//        return Observable.just(selectedUser)
//    }

    companion object {
        private val POSTS_AMOUNT = 100
        private val GENERATION_INTERVAL = 1000L //1 sec
    }

}