package com.vcmanea.a05_activity

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.vcmanea.a05_activity.R
import com.vcmanea.a05_activity.databinding.ActivityMainBinding
import timber.log.Timber

lateinit var binding: ActivityMainBinding
private const val TAG = "MainActivity"
private const val SCORE="score_value"
class MainActivity : AppCompatActivity() {

    lateinit var timer:Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Initialize timer and LifeCycleObserver class
        timer=Timer(this.lifecycle)
        var score = 0
        if(savedInstanceState!=null) {
            score=savedInstanceState.getInt(SCORE)
        }
        binding.scoreTxt.text=score.toString()

        binding.addBtn.setOnClickListener {
          score++
            binding.scoreTxt.text=score.toString()
        }



    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText("Random Text")
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }


    override fun onStart() {
    //Timer.runClock()
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Timber.i("onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause called")
    }

    override fun onStop() {

        super.onStop()
      //  Timer.pauseClock()
        Timber.i("onStop called")
    }


    override fun onSaveInstanceState(outState: Bundle) {
        //always call the super method, it automatically saves some data for you, such as EditTexts(as long they have an id).
        Timber.i("onSavedInstanceState Called")
        val sum=binding.scoreTxt.text.toString().toInt()
        outState.putInt(SCORE,sum)
        super.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy called")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shareMenu -> onShare();
            else->    return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }


}