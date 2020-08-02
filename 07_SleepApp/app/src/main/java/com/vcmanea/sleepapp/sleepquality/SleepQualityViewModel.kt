package com.vcmanea.sleepapp.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcmanea.sleepapp.database.SleepDatabaseDao
import kotlinx.coroutines.*

class SleepQualityViewModel (private val sleepNightKey: Long=0L,val databaseDao: SleepDatabaseDao):
    ViewModel() {
    //Job
    private val viewModelJob= Job()
    //UI scope
    private val uiScope= CoroutineScope(Dispatchers.Main+viewModelJob)

    //encapsulation
    private val _navigateToSleepTracker=MutableLiveData<Boolean?>()
    val navigateToSleepTracker:LiveData<Boolean?>
    get()=_navigateToSleepTracker

    fun doneNavigation(){
        _navigateToSleepTracker.value=null
    }





    fun onSetSleepQuality(quality: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val tonight=databaseDao.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality=quality
                databaseDao.update(tonight)
            }
            _navigateToSleepTracker.value=true
        }

    }









    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}

