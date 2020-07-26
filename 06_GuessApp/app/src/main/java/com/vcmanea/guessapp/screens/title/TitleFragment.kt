package com.vcmanea.guessapp.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vcmanea.guessapp.R
import com.vcmanea.guessapp.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding= DataBindingUtil.inflate<FragmentTitleBinding>(inflater,R.layout.fragment_title,container,false)
       binding.playBtn.setOnClickListener {
           it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
       }


        return binding.root
    }


}