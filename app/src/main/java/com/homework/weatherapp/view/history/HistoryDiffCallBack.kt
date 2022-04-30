package com.homework.weatherapp.view.history

import androidx.recyclerview.widget.DiffUtil
import com.homework.weatherapp.model.Weather

class HistoryDiffCallBack(
    private val oldList: List<Weather>,
    private val newList: List<Weather>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].city != newList[newItemPosition].city -> false
            oldList[oldItemPosition].fellsLike != newList[newItemPosition].fellsLike -> false
            oldList[oldItemPosition].temperature != newList[newItemPosition].temperature -> false
            oldList[oldItemPosition].condition != newList[newItemPosition].condition -> false
            oldList[oldItemPosition].humidity != newList[newItemPosition].humidity -> false
            oldList[oldItemPosition].icon != newList[newItemPosition].icon -> false
            else -> true
        }
    }
}
