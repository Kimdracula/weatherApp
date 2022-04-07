package com.homework.weatherapp.view.main

import androidx.recyclerview.widget.DiffUtil
import com.homework.weatherapp.model.Weather

class WeatherDiffCallback(
    private val oldList: List<Weather>,
    private val newList: List<Weather>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].city != newList[newItemPosition].city -> false
            oldList[oldItemPosition].fellsLike != newList[newItemPosition].fellsLike -> false
            oldList[oldItemPosition].temperature != newList[newItemPosition].temperature -> false
            else -> true
        }
    }
}