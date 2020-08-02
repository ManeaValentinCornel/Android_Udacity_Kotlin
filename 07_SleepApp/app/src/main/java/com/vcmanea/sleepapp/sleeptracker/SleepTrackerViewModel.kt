package com.vcmanea.sleepapp.sleeptracker
// Copyright 2018, The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.vcmanea.sleepapp.R
import com.vcmanea.sleepapp.database.SleepDatabaseDao
import com.vcmanea.sleepapp.database.SleepNightEntity
import kotlinx.coroutines.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat

//AndroidViewModel is the same as the ViewModel, but it take the application context as a parameter and makes it available as a property
//The ViewModel needs access to the data in the database, which is through the interface defined in the DAO
//and we need application context so we can access resources such as string and styles
class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application):AndroidViewModel(application) {

    private val _tonight=MutableLiveData<SleepNightEntity?>()
    val tonight:LiveData<SleepNightEntity?>
    get() = _tonight

    //database.getALlNight if we look into the DAO, will that in returns LiveData,w hich is a room feature,
    //every time the data in the database changesn the LiveData<List<SleepNightEntity>> is updated to show the latest data
    //you never need to call set value on the LiveData or update it
    //Room handles updating the data to always match the database for you
    //Since the nights have always has the latst data we can easily display the data into our view.

    val nightsList=database.getAllNight()

    //********************************************************FORMAT DATA ***************************************************************//

//    //Transformation will be calculated lazily, and will be returned onyl when LiveData is observed
//    val nightsString=Transformations.map(nightsList){
//        formatNights(it,application.resources)
//    }
//
//    /**
//     * Takes a list of SleepNights and converts and formats it into one string for display.
//     *
//     * For display in a TextView, we have to supply one string, and styles are per TextView, not
//     * applicable per word. So, we build a formatted string using HTML. This is handy, but we will
//     * learn a better way of displaying this data in a future lesson.
//     *
//     * @param   nights - List of all SleepNights in the database.
//     * @param   resources - Resources object for all the resources defined for our app.
//     *
//     * @return  Spanned - An interface for text that has formatting attached to it.
//     *           See: https://developer.android.com/reference/android/text/Spanned
//     */
//    fun formatNights(nights: List<SleepNightEntity>, resources: Resources): Spanned {
//        val sb = StringBuilder()
//        sb.apply {
//            append(resources.getString(R.string.title))
//            nights.forEach {
//                append("<br>")
//                append(resources.getString(R.string.start_time))
//                append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
//                if (it.endTimeMilli != it.startTimeMilli) {
//                    append(resources.getString(R.string.end_time))
//                    append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
//                    append(resources.getString(R.string.quality))
//                    append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
//                    append(resources.getString(R.string.hours_slept))
//                    // Hours
//                    append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
//                    // Minutes
//                    append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
//                    // Seconds
//                    append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
//                }
//            }
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
//        } else {
//            return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
//        }
//    }

    /**
     * Take the Long milliseconds returned by the system and stored in Room,
     * and convert it to a nicely formatted string for display.
     *
     * EEEE - Display the long letter version of the weekday
     * MMM - Display the letter abbreviation of the nmotny
     * dd-yyyy - day in month and full year numerically
     * HH:mm - Hours and minutes in 24hr format
     */

    //TRANSFORMATION MAPs FOR BTN VISIBILITY

    //if the tonight does't have a value, it should be visible
    val startBtnVisibility=Transformations.map(tonight){
        null==it
    }

    //if the night doesn't have a value, it should not be visible
    val stopButtonVisibility=Transformations.map(tonight){
        null!==it
    }

    val clearButtonVisible=Transformations.map(nightsList){
        it?.isNotEmpty()
    }





    //********************************************************COROUTINES I ******************************************************************//
    //PATTERN
//    fun someWorkToBeDone(){
//        //1 We launch a coroutine that runs on the main UI thread because the result affects the UI
//        uiScope.launch {
//            //2 Inside we call a suspend function do to the long running work so that we don't block the UI thread while waiting for the result
//            suspendFunction()
//        }
//    }
//    //3 Then we define the suspend function
//    private suspend fun suspendFunction(){
//        //The long running work doesn't have anything to do with the UI, so we switch to the IO context,
//        //-->so that we can run in a thread pool that is optimized and set aside for these operations.
//        //Suspend withContext() coroutine function should be called only from a coroutine or another suspended function
//        withContext(Dispatchers.IO){
//            //longRunningWork()
//        }
//    }

    //********************************************************COROUTINES II ******************************************************************//

    //1 JOB
    //to manage all the coroutines we need a job, that allows us to cancel all coroutines started by this ViewModel
    //when the ViewModel is no longer used and destroyed, so we don;t end up with coroutines that have nowhere to return to
    //when the ViewModel is no longer used and destroyed the onCleared() method is called, will override this function to cancel all coroutines started from the ViewModel
    private var viewModelJobs= Job()

    //2 SCOPE
    //The scope determines what thread the coroutine will run on, and it also need to know about the job
    //To get the scope, we ask for an instance of coroutine scope, and pass the dispatcher and the job
    //Dispatchers.Main means coroutines launched in the UI scope will run on the main thread
    //->This is sensible for many coroutine started by a ViewModel, as they will eventually result in an update of the UI after performing some processing
    private val uiScope= CoroutineScope(Dispatchers.Main + viewModelJobs)


    //you must create your job and coroutineScope before the init block.
    init{
        initializeTonight()
    }

    //GET TONIGHT
    private fun initializeTonight(){
        uiScope.launch {
            _tonight.value=getTonightFromDatabase()
        }
    }
    private suspend fun getTonightFromDatabase(): SleepNightEntity?{
        return withContext(Dispatchers.IO){
            var night=database.getTonight()
            //if the start time and the end time are the same we know we are continuing with an existing night
            if(night?.endTimeMilli != night?.startTimeMilli){
                night=null
            }
            night
        }
    }

    //INSERT TONIGHT
    fun onStartTracking(){
        uiScope.launch {
            val newNight=SleepNightEntity()
            insertTonight(newNight)
            _tonight.value=getTonightFromDatabase()
        }
    }
    private suspend fun insertTonight(night:SleepNightEntity){
        withContext(Dispatchers.IO){
            database.insert(night)
        }
    }

    //STOP TONIGHT
    fun onStopTracking(){
      uiScope.launch {
        //return from the launch not from the lambda
        val oldNightEntity=tonight.value?: return@launch
          oldNightEntity.endTimeMilli=System.currentTimeMillis()
          update(oldNightEntity)
      }
    }
    private suspend fun update(night: SleepNightEntity){
        withContext(Dispatchers.IO){
            database.update(night)
        }
    }

    //CLEAR TONIGHT
    fun onClear(){
        uiScope.launch {
            clear()
            _tonight.value=null
        }
    }
    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJobs.cancel()

    }

    //Navigate
    private val _navigateToSleepQuality=MutableLiveData<Long>()
    val navigateToSleepQuality:LiveData<Long>
    get()=_navigateToSleepQuality

    fun onSleepNightClicked(id: Long){
        _navigateToSleepQuality.value=id
    }

    fun OnSleepDataQualityNavigated(){
        _navigateToSleepQuality.value=null
    }



}