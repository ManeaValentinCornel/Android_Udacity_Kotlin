package com.vcmanea.a05_activity

import android.app.Application
import timber.log.Timber

class PusherApplicationClass:Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}