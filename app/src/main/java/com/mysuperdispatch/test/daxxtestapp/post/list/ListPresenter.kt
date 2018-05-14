package com.mysuperdispatch.test.daxxtestapp.post.list

import android.util.Log
import com.mysuperdispatch.test.daxxtestapp.util.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class ListPresenter(private val mListView: ListContract.ListView) : ListContract.ListPresenter {

    private val mRepository = Injection.getRepositoryInstance()
    private var mySubscriber: MySubscriber = MySubscriber(mListView)
    private var lastShownDate: Long = 0
    private var lastSmallestShownDate: Long = Long.MAX_VALUE

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
        lastShownDate = 0
        lastSmallestShownDate = Long.MAX_VALUE
    }

    override fun getPostsRefresh() {
        mRepository.getPostsRefresh(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastShownDate = postsList.maxBy { post -> post.index }?.index!!
                        lastSmallestShownDate = postsList.minBy { post -> post.index }?.index!!
                    }
                    mListView.showPosts(postsList)
                }, { error ->
                    Log.e(TAG, error.message)
                    mListView.showErrorScreen()
                })
    }

    override fun getPostsPerPage() {
        mRepository.getPostsPerPage(lastSmallestShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastSmallestShownDate = postsList.minBy { post -> post.index }?.index!!
                    }
                    mListView.addPostsPerPage(postsList.reversed())
                }, { error ->
                    Log.e(TAG, error.message)
                    mListView.showErrorToast()
                })
    }

    override fun getNewPosts() {
        mRepository.getNewPosts(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.isNotEmpty()) {
                        lastShownDate = postsList.maxBy { post -> post.index }?.index!!
                        if (lastSmallestShownDate == Long.MAX_VALUE) {
                            lastSmallestShownDate = postsList.minBy { post -> post.index }?.index!!
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
        mRepository.getNewPostsCount(lastShownDate)
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

