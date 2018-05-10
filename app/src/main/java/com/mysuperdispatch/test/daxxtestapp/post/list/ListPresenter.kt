package com.mysuperdispatch.test.daxxtestapp.post.list

import android.util.Log
import com.mysuperdispatch.test.daxxtestapp.data.local.entites.Post
import com.mysuperdispatch.test.daxxtestapp.util.Injection
import io.reactivex.Single
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
    }

    override fun getPostsRefresh() {
        Log.e(TAG, "lastShownDate before " + lastShownDate)
        Log.e(TAG, "lastSmallestShownDate before " + lastSmallestShownDate)
        mRepository.getPostsRefresh(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    mListView.showPosts(postsList)
                    if (postsList.size > 0) {
                        val max = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                        val min = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                        Log.e(TAG, "lastShownDate  setting " + max)
                        Log.e(TAG, "lastSmallestShownDate setting " + min)
                        lastShownDate = max
                        lastSmallestShownDate = min
                    }
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    override fun getPostsPerPage() {
        Log.e(TAG, "lastShownDate before " + lastShownDate)
        Log.e(TAG, "lastSmallestShownDate before " + lastSmallestShownDate)
        mRepository.getPostsPerPage(lastSmallestShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    mListView.showPosts(postsList)
                    if (postsList.size > 0) {
                        val max = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                        val min = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                        Log.e(TAG, "lastShownDate  setting " + max)
                        Log.e(TAG, "lastSmallestShownDate setting " + min)
                        lastShownDate = max
                        lastSmallestShownDate = min
                    }
                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }

    override fun getNewPosts() {
        Log.e(TAG, "lastShownDate before" + lastShownDate)
        Log.e(TAG, "lastSmallestShownDate before" + lastSmallestShownDate)
        mRepository.getNewPosts(lastShownDate).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ postsList ->
                    mListView.addNewPosts(postsList)
                    if (postsList.size > 0) {
                        val max = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                        val min = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                        Log.e(TAG, "lastShownDate  setting " + max)
                        Log.e(TAG, "lastSmallestShownDate setting " + min)
                        lastShownDate = postsList.maxBy { post -> post.publishedAt }?.publishedAt!!
                        lastSmallestShownDate = postsList.minBy { post -> post.publishedAt }?.publishedAt!!
                    }

                }, { error ->
                    Log.e(TAG, error.message) //TODO show error screen
                })
    }


    companion object {
        private val TAG = ListPresenter::class.java.simpleName
    }

//    var mySubscriber: MySubscriber = MySubscriber(mListView)

    override fun getNewPostsCount() {
//        mySubscriber.dispose()
//        mRepository.getNewPostsCount(lastShownDate)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(mySubscriber)


        mRepository.getNewPostsCount(lastShownDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ numberOfNewPosts ->
                    mListView.updateNewPostsCounter(numberOfNewPosts)
                }, { error ->
                    Log.e(TAG, error.message)
                })
    }

//    class MySubscriber(private val mListView: ListContract.ListView) : DisposableSubscriber<Long>() {
//        override fun onComplete() {
//
//        }
//
//        override fun onNext(numberOfNewPosts: Long?) {
//            mListView.updateNewPostsCounter(numberOfNewPosts!!)
//        }
//
//        override fun onError(t: Throwable?) {
//            Log.e(TAG, t!!.message) //TODO handle
//        }


}

