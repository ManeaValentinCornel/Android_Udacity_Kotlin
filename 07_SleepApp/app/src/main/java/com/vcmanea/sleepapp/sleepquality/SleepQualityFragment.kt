package com.vcmanea.sleepapp.sleepquality

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.vcmanea.sleepapp.R
import com.vcmanea.sleepapp.database.SleepDatabase
import com.vcmanea.sleepapp.databinding.FragmentSleepQualityBinding


class SleepQualityFragment : Fragment() {


    private lateinit var binding: FragmentSleepQualityBinding
    private lateinit var factory:SleepQualityViewModelFactory
    private val viewModel:SleepQualityViewModel by viewModels{factory}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //View binding
        binding=FragmentSleepQualityBinding.inflate(inflater)
        //arguments
        val arguments=SleepQualityFragmentArgs.fromBundle(requireArguments())
        //application
        val application= requireNotNull(this.activity).application
        //data source
        val dataSource=SleepDatabase.getDatabase(application).sleepDatabaseDao
        //factory
        factory= SleepQualityViewModelFactory(arguments.sleepNightKey,dataSource)
        //observer
        viewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {navigate->
            thisIsOver()
        })
        setListeners()







        return binding.root




    }

    private fun setListeners(){
        binding.apply {
            val clickableViews: List<View> = listOf(imageZero,imageOne,imageTwo,imageThree,imageFour,imageFive)
            clickableViews.forEachIndexed{index,view ->
                view.setOnClickListener{
                    viewModel.onSetSleepQuality(index)
                    viewModel.doneNavigation()
                }
            }
        }
    }

    private fun thisIsOver(){
        findNavController(this).navigate(R.id.action_sleepQuality_to_sleepTrackerFragment)
    }

}