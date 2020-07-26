package com.vcmanea.guessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //timber
        Timber.i("onCreate called")
        //nav controller
        navController = this.findNavController(R.id.nav_host_fragment)
        //back btn
        NavigationUI.setupActionBarWithNavController(this, navController)

    }


    //BackBtn Nav
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()

    }
}