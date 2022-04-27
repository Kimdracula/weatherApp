package com.homework.weatherapp.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.databinding.HistoryRecycleItemBinding
import com.homework.weatherapp.model.Weather

class HistoryAdapter(
    private var data: List<Weather> = listOf()
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setData(newData: List<Weather>) {
        this.data = newData
        notifyDataSetChanged()
        TODO("DIFFUTIL")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HistoryRecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    inner class ViewHolder(private val binding: HistoryRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            with(binding) {
                tvCityName.text = weather.city.name
                tvTemperature.text = weather.temperature.toString()
                tvFeelsLike.text = weather.fellsLike.toString()
            }
        }
    }
}