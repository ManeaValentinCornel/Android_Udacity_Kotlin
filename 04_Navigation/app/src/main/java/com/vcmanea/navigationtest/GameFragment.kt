package com.vcmanea.navigationtest


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vcmanea.navigationtest.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //DataBiding
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(inflater, R.layout.fragment_game, container, false)

        //WIN
        binding.gameWinBtn.setOnClickListener {
            //Nav
            it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToWinFragment("Valentin won the game"))
        }
        //LOSE
        binding.gameLostBtn.setOnClickListener {
            //Nav
            it.findNavController().navigate(GameFragmentDirections.actionGameFragmentToLoseFragment("The game is lost by Valentin"))
        }

        return binding.root
    }
}