package com.vcmanea.guessapp.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.vcmanea.guessapp.R
import com.vcmanea.guessapp.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    //viewModel
    val viewModel: GameViewModel by viewModels()

    //binding
    private lateinit var binding: FragmentGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Observe LiveData gameOverEvent
        viewModel.eventGameFinish.observe(this, Observer { hasFinished ->
            if (hasFinished) {
                gameIsOver()
                viewModel.onGameFinishComplete()
            }
        })


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //binding
        binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater, R.layout.fragment_game, container, false)
        //set the viewmodel for data binding - this allows the bound layotu access to all..
        binding.gameViewModel=viewModel
        //--> in order to allow us to use liveData to automatically the data binding layouts
        binding.setLifecycleOwner(this)
//        implement into the xml layout using a data biding expression
//        binding.skipBtn.setOnClickListener {
//            viewModel.onSkip()
//        }


        binding.gotitBtn.setOnClickListener {
            viewModel.onCorrect()
        }


//all thso code has been move to layout by using data-binding
//        //Observe liveData SCORE
//        //Create the observer which updates the UI.
//        val scoreObserver = Observer<Int> { newScore ->
//            binding.scoreTxtGame.setText(getString(R.string.scoreTxt, newScore.toString().toInt()))
//        }
        //Observe the LiveData, passing in this activity as the LifecycleOwner and the observer
//        viewModel.score.observe(viewLifecycleOwner, scoreObserver)


        //Observe liveData WORD
        viewModel.word.observe(viewLifecycleOwner, Observer<String> {
            binding.guessTxt.text = it
        })


        //Observe liveData TIME
        viewModel.time.observe(viewLifecycleOwner, Observer<String> {
            binding.timerTxt.text = it
        })


        return binding.root

    }

    private fun gameIsOver() {
        Toast.makeText(this.activity, "Game has finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }


}