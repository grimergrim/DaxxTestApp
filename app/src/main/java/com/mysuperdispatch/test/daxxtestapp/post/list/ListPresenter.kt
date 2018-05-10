package com.mysuperdispatch.test.daxxtestapp.post.list

import android.util.Log
import com.mysuperdispatch.test.daxxtestapp.util.Injection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class ListPresenter(private val mListView: ListContract.ListView) : ListContract.ListPresenter {

    private val mRepository = Injection.getRepositoryInstance()
    private var lastShownDate: Long = 0
    private var lastSmallestShownDate: Long = Long.MAX_VALUE

    override fun startPostGeneration() {
        mRepository.startPostGeneration()
    }

    override fun stopPostGeneration() {
        mRepository.stopPostGeneration()
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
                    if (postsList.size > 0) {
                        lastShownDate = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                        lastSmallestShownDate = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                    }
                    mListView.showPosts(postsList)
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    override fun getPostsPerPage() {
        mRepository.getPostsPerPage(lastSmallestShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.size > 0) {
                        lastSmallestShownDate = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                    }
                    mListView.addPostsPerPage(postsList.reversed())
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    //TODO handle errors every were

    override fun getNewPosts() {
        mRepository.getNewPosts(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    if (postsList.size > 0) {
                        lastShownDate = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                    }
                    mListView.addNewPosts(postsList)
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    override fun onPause() {
        mySubscriber.dispose()
    }

    companion object {
        private val TAG = ListPresenter::class.java.simpleName
    }

    var mySubscriber: MySubscriber = MySubscriber(mListView)

    override fun getNewPostsCount() {
        mySubscriber.dispose()
        mySubscriber = MySubscriber(mListView)
        Log.e(TAG, "lastShownDate " + lastShownDate)
        mRepository.getNewPostsCount(lastShownDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(mySubscriber)
    }

    class MySubscriber(private val mListView: ListContract.ListView) : DisposableSubscriber<Long>() {
        override fun onComplete() {
            Log.e(TAG, "Comleted")
        }

        override fun onNext(numberOfNewPosts: Long?) {
            Log.e(TAG, "onNext " + numberOfNewPosts)
            mListView.updateNewPostsCounter(numberOfNewPosts!!)
        }

        override fun onError(t: Throwable?) {
            Log.e(TAG, "Error")
            Log.e(TAG, t!!.message) //TODO handle
        }

    }
}

