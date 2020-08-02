package com.vcmanea.sleepapp.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.vcmanea.sleepapp.R
import com.vcmanea.sleepapp.database.SleepDatabase
import com.vcmanea.sleepapp.databinding.FragmentSleepTrackerBinding


class SleepTrackerFragment : Fragment() {

    private lateinit var sleepTrackerViewModelFactory: SleepTrackerViewModelFactory
    private val viewModel: SleepTrackerViewModel by viewModels { sleepTrackerViewModelFactory }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentSleepTrackerBinding.inflate(inflater)
        //Application , requireNotNull throws an IllegalArgumentException if the argument value is null
        val applicaton = requireNotNull(this.activity).application
        //Data Source, reference to a dataSource via a referece to the DAO
        val dataSource = SleepDatabase.getDatabase(applicaton).sleepDatabaseDao
        //ViewModel Factory
        sleepTrackerViewModelFactory = SleepTrackerViewModelFactory(dataSource, applicaton)
        //Adapter
        val adapter=SleepNightAdapter(SleepNightListener {
            nightId -> viewModel.onSleepNightClicked(nightId)

        })
        binding.nightsRecyclerView.adapter=adapter











        viewModel.nightsList.observe(viewLifecycleOwner, Observer {
            it.let {
                //update the adapter list, which tell if a new version of the list is available
                //it will diff the enw list against the old one and than run all the needed changes on the recycler view
                // adapter.submitList(it)
                adapter.addHeaderAndSubmitList(it)
            }
        })


        //BUTTONS VISIBILITY
        viewModel.startBtnVisibility.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.startBtn.visibility = View.VISIBLE
            }
            else {
                binding.startBtn.visibility = View.INVISIBLE

            }
        })
        viewModel.stopButtonVisibility.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.stopBtn.visibility = View.VISIBLE
            }
            else {

                binding.stopBtn.visibility = View.INVISIBLE

            }
        })
        viewModel.clearButtonVisible.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.clearBtn.visibility = View.VISIBLE
            }
            else {
                binding.clearBtn.visibility = View.INVISIBLE
            }
        })

        //BTN'S LISTENERS
        binding.startBtn.setOnClickListener {
            viewModel.onStartTracking()
        }
        binding.stopBtn.setOnClickListener {
            viewModel.onStopTracking()
            val id =viewModel.tonight.value?.nightId
            id?.let {id->
                val action = SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQuality(id)
                it.findNavController().navigate(action)
            }
        }
        binding.clearBtn.setOnClickListener {
            viewModel.onClear()
        }
        return binding.root
    }



}