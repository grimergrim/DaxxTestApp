package com.mysuperdispatch.test.daxxtestapp.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class PrefsUtils(context: Context) {

    private val mPrefs = context.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)

    fun saveFirstStart() {
        val editor = mPrefs.edit()
        editor.putBoolean(FIRST_START, false)
        editor.apply()
    }

    fun getFirstStart(): Boolean {
        return mPrefs.getBoolean(FIRST_START, true)
    }

    companion object {
        private val PREFS_FILENAME = "com.mysuperdispatch.test"
        private val FIRST_START = "firstStartHappened"
    }

}