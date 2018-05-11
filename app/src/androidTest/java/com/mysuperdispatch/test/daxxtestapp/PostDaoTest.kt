package com.mysuperdispatch.test.daxxtestapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostsDatabase
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PostsDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                PostsDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndCheckIndexTest() {
        database.postDao().insertPost(POST)
        val maxIndex = database.postDao().getMaxIndex()
        assertEquals(POST.index, maxIndex)
    }

    @Test
    fun insertAndCheckAmount() {
        val postsAmount = 20L
        for (i in 1..postsAmount) {
            database.postDao().insertPost(Post(TITLE + i, AUTHOR + i,
                    System.currentTimeMillis(), i))
        }
        val newPostsCount = database.postDao().getNewPostsCount(0).blockingFirst()
        assertEquals(postsAmount, newPostsCount)
    }

    companion object {
        private val POST = Post("Title", "Author", 13121241, 1)
        private const val TITLE = "Title"
        private const val AUTHOR = "Author"
    }
}
