package com.mysuperdispatch.test.daxxtestapp.post.list

import android.util.Log
import com.mysuperdispatch.test.daxxtestapp.util.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class ListPresenter(private val mListView: ListContract.ListView) : ListContract.ListPresenter {

    private val mRepository = Injection.getRepositoryInstance()
    private var mySubscriber: MySubscriber = MySubscriber(mListView)
    private var lastShownIndex: Long = 0
    private var lastSmallestShownIndex: Long = Long.MAX_VALUE

    override fun startPostGeneration() {
        mRepository.startPostGeneration()
    }

    override fun stopPostGeneration() {
        mRepository.stopPostGeneration()
    }

    override fun onPause() {
        mySubscriber.dispose()
    }

    override fun deletePosts() {
        mRepository.deletePosts()
        lastShownIndex = 0
        lastSmallestShownIndex = Long.MAX_VALUE
        getNewPostsCount()
    }

    override fun getPostsRefresh() {
        mRepository.getPostsRefresh(lastShownIndex).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastShownIndex = postsList.maxBy { post -> post.indexCounter }?.indexCounter!!
                        lastSmallestShownIndex = postsList.minBy { post -> post.indexCounter }?.indexCounter!!
                    }
                    mListView.showPosts(postsList)
                }, { error ->
                    Log.e(TAG, error.message)
                    mListView.showErrorScreen()
                })
    }

    override fun getPostsPerPage() {
        mRepository.getPostsPerPage(lastSmallestShownIndex).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastSmallestShownIndex = postsList.minBy { post -> post.indexCounter }?.indexCounter!!
                    }
                    mListView.addPostsPerPage(postsList.reversed())
                }, { error ->
                    Log.e(TAG, error.message)
                    mListView.showErrorToast()
                })
    }

    override fun getNewPosts() {
        mRepository.getNewPosts(lastShownIndex).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastShownIndex = postsList.maxBy { post -> post.indexCounter }?.indexCounter!!
                        if (lastSmallestShownIndex == Long.MAX_VALUE) {
                            lastSmallestShownIndex = postsList.minBy { post -> post.indexCounter }?.indexCounter!!
                        }
                    }
                    mListView.addNewPosts(postsList)
                }, { error ->
                    Log.e(TAG, error.message)
                    mListView.showErrorToast()
                })
    }

    override fun getNewPostsCount() {
        mySubscriber.dispose()
        mySubscriber = MySubscriber(mListView)
        mRepository.getNewPostsCount(lastShownIndex)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mySubscriber)
    }

    companion object {
        private val TAG = ListPresenter::class.java.simpleName
    }

    private class MySubscriber(private val mListView: ListContract.ListView) : DisposableSubscriber<Long>() {
        override fun onComplete() {
            Log.d(TAG, "Complete")
        }

        override fun onNext(numberOfNewPosts: Long?) {
            mListView.updateNewPostsCounter(numberOfNewPosts!!)
        }

        override fun onError(t: Throwable?) {
            Log.e(TAG, t!!.message)
        }

    }
}

