package com.mysuperdispatch.test.daxxtestapp.post.list

import android.util.Log
import com.mysuperdispatch.test.daxxtestapp.util.Injection
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListPresenter(private val mListView: ListContract.ListView) : ListContract.ListPresenter {

    private val mRepository = Injection.getRepositoryInstance()
    private var lastShownDate: Long = 0

    override fun startPostGeneration() {
        mRepository.startPostGeneration()
    }

    override fun stopPostGeneration() {
        mRepository.stopPostGeneration()
    }

    override fun deletePosts() {
        mRepository.deletePosts()
    }

    override fun getPosts() {
        mRepository.getPosts().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    mListView.showPosts(postsList)
                    lastShownDate = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    companion object {
        private val TAG = ListPresenter::class.java.simpleName
    }

    override fun getNewPostsCount() {
        mRepository.getNewPostsCount(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ numberOfNewPosts ->
                    mListView.updateNewPostsCounter(numberOfNewPosts)
                }, { error ->
                    Log.e(TAG, error.message) //TODO handle
                })
    }

}