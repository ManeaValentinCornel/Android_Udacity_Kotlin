package com.vcmanea.a08_marsapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vcmanea.a08_marsapp.databinding.FragmentDetailBinding


class DetailFragment:Fragment(){

    private lateinit var factory:DetailFragmentViewModelFactory
    private val viewModel: DetailViewModel by viewModels{factory}
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding=FragmentDetailBinding.inflate(inflater)
        val application= requireNotNull(activity).application
        val property=args.selectedProperty
        factory= DetailFragmentViewModelFactory(property,application)

        binding.setLifecycleOwner(this)
        binding.viewModel=viewModel

        return binding.root

    }

}