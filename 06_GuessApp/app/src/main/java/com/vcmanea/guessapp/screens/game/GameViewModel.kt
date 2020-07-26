package com.vcmanea.guessapp.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    companion object{
        private const val DONE=0L
        private const val ONE_SECOND=1000L
        private const val COUNTDOWN_TIME=60000L
    }

    private val timer: CountDownTimer

    lateinit var wordList: MutableList<String>

    private val _word= MutableLiveData<String>()
    val word:LiveData<String>
    get()=_word

    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
    get()=_score

    private val _eventGameFinish=MutableLiveData<Boolean>()
    val eventGameFinish:LiveData<Boolean>
    get()=_eventGameFinish

    private val _time=MutableLiveData<String>()
    val time:LiveData<String>
    get() = _time


    init {
        Timber.i("GameViewModel created !")
        shuffleList()
        _score.value=0
        _word.value=wordList.removeAt(0)
        _time.value=""
        //TIMER

        timer=object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(p0: Long) {
                _time.value=DateUtils.formatElapsedTime(p0/1000)
            }

            override fun onFinish() {
                _eventGameFinish.value=true
            }
        }
            .start()

    }


    private fun shuffleList() {
        wordList = mutableListOf(
            "railway",
            "soupjelly",
            "crow",
            "pop",
            "things",
            "lettuce",
            "tangible",
            "obtainable",
            "enchanted",
            "foregoing",
            "knowledgeable",
            "stove",
            "sparkiling",
            "endurable",
            "shocking",
            "wool",
            "wealth",
            "thightfisted",
            "soak",
            "spooky"
        )
        wordList.shuffle()
    }


    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            _word.value= wordList.removeAt(0)
        }
        else{
            _eventGameFinish.value=true
        }
    }

    fun onSkip() {
        _score.value=(score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value=(score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value=false
    }




    //To avoid memory leaks, you should always cancel a
    // CountDownTimer if you no longer need it. To do that, you can call:
    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Timber.i("GameViewModel destroyed")


    }



}