package com.vcmanea.navigationtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.vcmanea.navigationtest.databinding.FragmentLoseBinding

class LoseFragment : Fragment() {
    //Auto-generated sage-args class
    val args: LoseFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //DataBiding
        val binding = DataBindingUtil.inflate<FragmentLoseBinding>(inflater, R.layout.fragment_lose, container, false)
        val safeArgsValue=args.loseArgs
        binding.loseText.append(safeArgsValue)

        binding.loseBtnGoback.setOnClickListener {
            //Nav
         it.findNavController().navigate(R.id.action_loseFragment_to_gameFragment)

        }

        return binding.root

    }
}