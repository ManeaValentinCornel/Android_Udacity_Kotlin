package com.vcmanea.a08_marsapp.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vcmanea.a08_marsapp.databinding.GridViewBinding
import com.vcmanea.a08_marsapp.network.MarsProperty
/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */

class PhotoGridAdapter(private val onClickListener: OnClickListener): ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffUtilCallback){
    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder.from(parent)
    }
    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty=getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(marsProperty)
        }
        holder.bindTo(marsProperty)
    }





    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsProperty] information.
     */
    class MarsPropertyViewHolder(private var binding:GridViewBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindTo(marsProperty: MarsProperty){
            binding.property=marsProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MarsPropertyViewHolder {
                //val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_sleep_night, parent, false)
                //DATA BINDING to find the view at the compile time instead of finding them at the runtime
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridViewBinding.inflate(layoutInflater)
                return MarsPropertyViewHolder(binding)
            }
        }



    }
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffUtilCallback:DiffUtil.ItemCallback<MarsProperty>(){
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
         return oldItem.id==newItem.id
        }
        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
        return oldItem==newItem
        }
    }




    class OnClickListener(val clickListener: (marsProperty:MarsProperty) -> Unit){
        fun onClick(marsProperty: MarsProperty)=clickListener(marsProperty)
    }







}