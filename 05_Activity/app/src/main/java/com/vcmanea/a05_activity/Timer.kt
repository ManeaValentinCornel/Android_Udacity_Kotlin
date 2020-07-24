package com.vcmanea.a05_activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class Timer(lifeCycle: Lifecycle): LifecycleObserver {
    //the number of seconds counted since the time started
    var secondsCount=0
    var job1: Job?=null


    init {
        //this lyfecycle will observe that activity
        lifeCycle.addObserver(this)
    }


    fun dummyMethod(){
        Timber.i("I was called")
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun runClock(){

        job1=GlobalScope.launch {
            while(secondsCount<100){
                Timber.i("Time is at $secondsCount")
                secondsCount++
                delay(1000)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun pauseClock(){
        job1?.cancel()
    }








}