package com.vcmanea.guessapp

import android.app.Application
import timber.log.Timber

class TimberApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }


}