package com.homework.weatherapp.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.databinding.RecycleItemBinding
import com.homework.weatherapp.model.Weather

class MfAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var weatherList: List<Weather> = emptyList()
) :
    RecyclerView.Adapter<MfAdapter.ViewHolder>() {


    fun setWeatherList(newWeatherList: List<Weather>) {
        val diffUtil = WeatherDiffCallback(weatherList, newWeatherList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        weatherList = newWeatherList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    inner class ViewHolder(private val binding: RecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            with(binding){
           textViewCityName.text = weather.city.name
            root.setOnClickListener {
                onItemListClickListener.onItemClick(weather)
            }
        }
    }}

}
