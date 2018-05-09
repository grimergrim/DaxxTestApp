package com.mysuperdispatch.test.daxxtestapp.util

import android.content.Context
import com.mysuperdispatch.test.daxxtestapp.data.Repository
import com.mysuperdispatch.test.daxxtestapp.data.RepositoryImpl
import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSource
import com.mysuperdispatch.test.daxxtestapp.data.local.LocalDataSourceImpl
import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostDao
import com.mysuperdispatch.test.daxxtestapp.data.local.db.PostsDatabase

object Injection {

    private lateinit var mRepository: Repository
    private lateinit var mLocalDataSource: LocalDataSource

    private var initialized: Boolean = false

    fun initGraph(context: Context) {
        if (!initialized) {
            mLocalDataSource = LocalDataSourceImpl(provideUserDao(context))
            mRepository = RepositoryImpl(mLocalDataSource)
            initialized = true
        }
    }

    fun getRepositoryInstance(): Repository {
        return mRepository
    }

    private fun provideUserDao(context: Context): PostDao {
        val database = PostsDatabase.getInstance(context)
        return database.postDao()
    }

}