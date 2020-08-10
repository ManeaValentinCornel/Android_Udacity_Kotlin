package com.vcmanea.a08_marsapp.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vcmanea.a08_marsapp.R
import com.vcmanea.a08_marsapp.network.MarsProperty

class DetailViewModel(marsProperty:MarsProperty,app:Application) : AndroidViewModel(app){

private val _selectedProperty= MutableLiveData<MarsProperty>()
    val selectedProperty : LiveData<MarsProperty>
    get()=_selectedProperty

    init{
        _selectedProperty.value=marsProperty
    }


    val displayPropertyPrice=Transformations.map(selectedProperty){
        app.applicationContext.getString(when(it.isRental){
            true-> R.string.display_price_monthly_rental
            false-> R.string.display_price
        },it.price)
    }

}