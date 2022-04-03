package com.homework.weatherapp.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.databinding.RecycleItemBinding
import com.homework.weatherapp.model.Weather

class MfAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var citiesList: List<Weather> = listOf()
) :
    RecyclerView.Adapter<MfAdapter.ViewHolder>() {
    private var lastPosition = -1

    fun setWeatherList(list: List<Weather>) {
        this.citiesList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(citiesList[position])
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int = citiesList.size

    inner class ViewHolder(private val binding: RecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: Weather) {
            binding.textViewCityName.text = weather.city.name
            binding.root.setOnClickListener {
                onItemListClickListener.onItemClick(weather)
            }
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


}