package com.mysuperdispatch.test.daxxtestapp.data.local

import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostDao
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import io.reactivex.Flowable
import io.reactivex.Observable

class LocalDataSourceImpl(private val postDao: PostDao) : LocalDataSource {

    override fun generatePosts() {
        Thread(Runnable {
            if (isFirstStart()) {
                for (i in 1..POSTS_AMOUNT) {
                    postDao.insertPost(Post("Title" + i, "Author" + i, System.currentTimeMillis()))
                }
//                TODO("save that first start happened")
            }

            generatePost(getNumberOfPosts())

        }).start()
    }

    private fun generatePost(i: Long) {
//        Thread.sleep(GENERATION_INTERVAL)
//        postDao.insertPost(Post("Title" + i, "Author" + i, System.currentTimeMillis()))
//        generatePost(i + 1)
        //TODO("change for thread sleep")
    }

    private fun getNumberOfPosts(): Long {
        return 100
//        TODO("add long field to post and save i there and get max value here, or use ID if it's ok")
    }

    private fun isFirstStart(): Boolean {
        return true;
//        TODO("save bool to prefs and get it from there")
    }


    override fun savePost(post: Post) {
//        TODO("implement")
    }

    override fun getPosts(): Flowable<List<Post>> {
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