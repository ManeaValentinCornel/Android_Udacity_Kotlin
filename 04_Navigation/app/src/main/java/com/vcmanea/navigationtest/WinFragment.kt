package com.vcmanea.navigationtest

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.vcmanea.navigationtest.databinding.FragmentWinBinding

class WinFragment : Fragment() {
    //Auto-generated sage-args class
   val args: WinFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //BindingData
        val binding = DataBindingUtil.inflate<FragmentWinBinding>(inflater, R.layout.fragment_win, container, false)
        binding.winBtn.setOnClickListener {
            //Nav
            it.findNavController().navigate(R.id.action_winFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu,menu)
        //The problem is if there are not any activities that suppot our sharing the code will crash
        //if we can't share instead, we hide the share menu
        //check if the activity resolves
        if(null==getShareIntent2().resolveActivity(requireActivity().packageManager)){
            //System service called package manager which we can get form the activity
            //The package manager knows about every activity that is registered in the Android manifest across application
            //if it return null we fail to resolve our activity
            menu.findItem(R.id.share_menu)?.setVisible(false)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share_menu -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }



    private fun getShareIntent(): Intent {
        val stringArgs=args.winArgs
        //Create a new share implicit intent
        //We tell android we want the activities that are register with an intent filter to hand;e the send action
        val shareIntent=Intent(Intent.ACTION_SEND)
        //Android uses the mime type of our parameter to locate the correct activities to share too
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT,stringArgs)

        return shareIntent
    }

    private fun getShareIntent2(): Intent{
        val stringArgs=args.winArgs
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(stringArgs)
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess(){
        startActivity(getShareIntent2())
    }


}