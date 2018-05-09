package com.mysuperdispatch.test.daxxtestapp

import android.app.Application
import com.mysuperdispatch.test.daxxtestapp.util.Injection

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injection.initGraph(applicationContext)
    }
}