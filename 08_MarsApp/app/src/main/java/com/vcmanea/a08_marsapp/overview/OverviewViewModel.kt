package com.vcmanea.a08_marsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vcmanea.a08_marsapp.network.*
import kotlinx.coroutines.*

enum class MarsApiStatus {
    LOADING, ERROR, DONE

}

class OverviewViewModel : ViewModel() {


    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties


    //Navigate
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    fun displayProperyDetails(marsProperty: MarsProperty){
        _navigateToSelectedProperty.value=marsProperty
    }

    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value=null
    }
    //Navigate

    //always bvefore the init
    private var viewModelJob = Job()
    private val corotineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        getMarsRealEstateProperties()
    }

    private fun getMarsRealEstateProperties() {
        corotineScope.launch {
            var list: List<MarsProperty>? = null

            try {
                _status.value = MarsApiStatus.LOADING
                withContext(Dispatchers.IO) {
                    list = MarsRepository.getMars()
                    _status.value = MarsApiStatus.DONE

                }
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()


            }
            Log.d("OverviewViewModel", "${list?.get(0)} ")

            _properties.value = list
        }


    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
