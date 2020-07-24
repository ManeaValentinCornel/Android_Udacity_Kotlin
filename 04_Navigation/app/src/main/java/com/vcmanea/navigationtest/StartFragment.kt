package com.vcmanea.navigationtest

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.vcmanea.navigationtest.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding=DataBindingUtil.inflate<FragmentStartBinding>(layoutInflater,R.layout.fragment_start,container,false)
        //BindingData
        binding.startBtn.setOnClickListener{
            //Nav
            it.findNavController().navigate(R.id.action_startFragment_to_gameFragment)
        }
        //Overflow Menu
        setHasOptionsMenu(true)
        return binding.root
    }

    //Overflow Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}