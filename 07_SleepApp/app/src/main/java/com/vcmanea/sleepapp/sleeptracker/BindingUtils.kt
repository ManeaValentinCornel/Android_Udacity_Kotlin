package com.vcmanea.sleepapp.sleeptracker

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vcmanea.sleepapp.R
import com.vcmanea.sleepapp.database.SleepNightEntity
import java.text.SimpleDateFormat


@BindingAdapter("sleepDurationFormatted")
fun setSleepDuration(view:TextView, item: SleepNightEntity?) {
    item?.let {
        view.text = convertDurationToFormatted(it.startTimeMilli, item.endTimeMilli)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNightEntity?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)

    }
}

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNightEntity?) {
    item?.let {
     setImageResource(when(item.sleepQuality){
     0-> R.drawable.ic_sleep_0
         1-> R.drawable.ic_sleep_1
         2-> R.drawable.ic_sleep_2
         3-> R.drawable.ic_sleep_3
         4-> R.drawable.ic_sleep_4
        else-> R.drawable.ic_sleep_active

     })}
    }


    //**************************************************    Format Data *********************************************************//
    @SuppressLint("SimpleDateFormat")
    fun convertDurationToFormatted(systemTime: Long, endTime: Long): String {
        return SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
            .format(systemTime).toString() + "\n" + SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
            .format(endTime).toString()
    }

    /**
     * Returns a string representing the numeric quality rating.
     */
    fun convertNumericQualityToString(quality: Int, resources: Resources): String {
        var qualityString = resources.getString(R.string.three_ok)
        when (quality) {
            -1 -> qualityString = "--"
            0 -> qualityString = resources.getString(R.string.zero_very_bad)
            1 -> qualityString = resources.getString(R.string.one_poor)
            2 -> qualityString = resources.getString(R.string.two_soso)
            4 -> qualityString = resources.getString(R.string.four_pretty_good)
            5 -> qualityString = resources.getString(R.string.five_excellent)
        }
        return qualityString
    }

