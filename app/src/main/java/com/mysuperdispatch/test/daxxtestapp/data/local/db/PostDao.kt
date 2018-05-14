package com.mysuperdispatch.test.daxxtestapp.data.local.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post

import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Query("DELETE FROM posts")
    fun deleteAllPosts()

    @Query("SELECT MAX(`indexCounter`) FROM posts")
    fun getMaxIndex(): Long

    @Query("SELECT * FROM posts ORDER BY indexCounter DESC LIMIT 10")
    fun getPostsRefresh(): Single<List<Post>>

    @Query("SELECT COUNT(*) FROM posts WHERE indexCounter > :lastShownIndex")
    fun getNewPostsCount(lastShownIndex: Long): Flowable<Long>

    @Query("SELECT * FROM posts WHERE indexCounter > :lastShownIndex ORDER BY indexCounter ASC")
    fun getNewPosts(lastShownIndex: Long): Single<List<Post>>

    @Query("SELECT * FROM (SELECT * FROM posts WHERE indexCounter < :lastSmallestShownIndex ORDER BY indexCounter DESC LIMIT 10) T1 ORDER BY indexCounter")
    fun getPostsPerPage(lastSmallestShownIndex: Long): Single<List<Post>>

}
