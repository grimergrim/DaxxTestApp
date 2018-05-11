package com.mysuperdispatch.test.daxxtestapp.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post

@Database(entities = [(Post::class)], version = 1)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostsDatabase? = null
        private const val DB_NAME = "MySuperDispatch.db"

        fun getInstance(context: Context): PostsDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        PostsDatabase::class.java, DB_NAME)
                        .build()
    }
}
