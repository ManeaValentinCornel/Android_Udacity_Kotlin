package com.vcmanea.guessapp.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.vcmanea.guessapp.R
import com.vcmanea.guessapp.TimberApplication
import com.vcmanea.guessapp.databinding.FragmentScoreBinding
import timber.log.Timber


class ScoreFragment : Fragment() {


    private lateinit var viewModelFactory: ScoreViewModelFactory

    private val viewModel: ScoreViewModel by viewModels{viewModelFactory}

    val args: ScoreFragmentArgs by navArgs()
    private lateinit var binding: FragmentScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //BindingClass
        binding = DataBindingUtil.inflate<FragmentScoreBinding>(inflater, R.layout.fragment_score, container, false)
        //ViewModelFactory -> Construct the viewModel factory with a parameter of tiype args
        viewModelFactory= ScoreViewModelFactory(args.argInt)
        //View models need to be called only after the ViewModelFactory has been initialized
        //Observe the score
        viewModel.score.observe(viewLifecycleOwner,Observer{
            binding.myScore.setText(getString(R.string.scoreTxt,it))
        })



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}