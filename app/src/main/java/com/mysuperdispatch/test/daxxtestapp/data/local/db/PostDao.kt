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

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: String): Flowable<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Query("DELETE FROM posts")
    fun deleteAllPosts()

    @Query("SELECT * FROM posts ORDER BY publishedAt DESC")
    fun getAllPosts(): Single<List<Post>>

    @Query("SELECT * FROM (SELECT * FROM posts ORDER BY publishedAt DESC LIMIT 10) T1 ORDER BY publishedAt")
    fun getPostsPerPage(): Flowable<List<Post>>

    @Query("SELECT MAX(`index`) FROM posts")
    fun getMaxIndex(): Long

    @Query("SELECT COUNT(*) FROM posts WHERE publishedAt > :lastShownDate")
    fun getNewPostsCount(lastShownDate: Long): Flowable<Long>

}
