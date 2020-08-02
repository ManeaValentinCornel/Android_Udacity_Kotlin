package com.vcmanea.sleepapp.sleeptracker


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vcmanea.sleepapp.R
import com.vcmanea.sleepapp.database.SleepNightEntity
import com.vcmanea.sleepapp.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1


//List adapter help you to build a recycler view adapter that;s backed by a list
//List adapter will take care of keeping track of the list for you and even notyging the adapter when the list is updated

//BeforeHeader
//class SleepNightAdapter(val clickListener: SleepNightListener) :
//    ListAdapter<SleepNightEntity, SleepNightAdapter.SleepNightViewHolder>(SleepNightDiffCallback()) {
// }
//  1
//  List adapter will keep track of the list of us

//    var nightsList = listOf<SleepNightEntity>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

//  2
// The even get item count is no longer needed,List adapter covers this functionality
//    override fun getItemCount(): Int = nightsList.size

class SleepNightAdapter(val clickListener: SleepNightListener) :
        ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {


    //convert a ListSleepNight into a list DataItemList, on a background thread
    //after we get the list we have to get bnack to the UI thread in order to call the submit list method

    private val adapterScope= CoroutineScope(Dispatchers.Default)
    fun addHeaderAndSubmitList(list:List<SleepNightEntity>?){
       adapterScope.launch {
        val items=when(list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
        }
           withContext(Dispatchers.Main){
               submitList(items)
           }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER-> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM-> SleepNightViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header-> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem-> ITEM_VIEW_TYPE_ITEM
            else ->  throw ClassCastException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SleepNightViewHolder-> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }


    //  VIEW HOLDER 1
    //no outer class one will ever call the constructor of the ViewHolder because it has  companion object which return the class
    class SleepNightViewHolder private constructor(val binding: ListItemSleepNightBinding) :
        RecyclerView.ViewHolder(binding.root) {
//      DataBinding
//        you don;t need to use this properties at all, because the data binding will cache the lookups so there is no reason for us the declare this properties
//        val sleepLength: TextView = binding.sleepLength
//        val quality: TextView = binding.qualityString
//        val qualityImage: ImageView = binding.qualityImage

        fun bind(item: SleepNightEntity, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            //Execute Pending Binding when using binding adapters
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SleepNightViewHolder {
                //val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_sleep_night, parent, false)
                //DATA BINDING to find the view at the compile time instead of finding them at the runtime
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return SleepNightViewHolder(binding)
            }
        }
    }
}

//  VIEW HOLDER 2
class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): TextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return TextViewHolder(view)
        }
    }
}


//Diff Util
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        //data calss autmatically define equals that why we can use == to comapre the content of the objects
        return oldItem == newItem
    }
}

//Listener
//Study further
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNightEntity) = clickListener(night.nightId)
}

//Header
sealed class DataItem {
    abstract val id: Long

    data class SleepNightItem(val sleepNight: SleepNightEntity) : DataItem() {
        override val id: Long = sleepNight.nightId
    }

    object Header : DataItem() {
        override val id: Long = Long.MIN_VALUE
    }
}





