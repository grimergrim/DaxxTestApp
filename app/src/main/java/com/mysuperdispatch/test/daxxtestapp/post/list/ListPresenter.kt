package com.mysuperdispatch.test.daxxtestapp.post.list

import com.mysuperdispatch.test.daxxtestapp.util.Injection

class ListPresenter(private val mListView: ListContract.ListView) : ListContract.ListPresenter {

    private val mRepository = Injection.getRepositoryInstance()

    override fun launch() {
        mRepository.populateDb()
    }




    override fun getPosts() {
//        mRepository.getPostsLocal().observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({ postsList ->
//                    mListView.showPosts(postsList)
//                }, { error ->
//                    Log.e(TAG, error.message)
//                })
    }

    companion object {
        private val TAG = ListPresenter::class.java.simpleName
    }

}