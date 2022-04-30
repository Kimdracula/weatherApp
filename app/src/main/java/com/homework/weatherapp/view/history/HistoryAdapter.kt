package com.homework.weatherapp.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.databinding.HistoryRecycleItemBinding
import com.homework.weatherapp.model.Weather
import com.homework.weatherapp.utils.*

class HistoryAdapter(
    private var data: List<Weather> = listOf()
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setData(newData: List<Weather>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HistoryRecycleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(holder,data[position])
    }

    override fun getItemCount(): Int = data.size


    inner class ViewHolder(private val binding: HistoryRecycleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: ViewHolder, weather: Weather) {
            with(binding) {
                tvCityName.text = weather.city.name
                tvTemperature.append(weather.temperature.toString())
                tvFeelsLike.append(weather.fellsLike.toString())
                tvCondition.append(weather.condition)
                tvHumidity.append(weather.humidity.toString())
                loadSvg(holder.itemView.context,
                    "$SCHEME//$AUTHORITY_ICON/$END_POINT_ICON/${weather.icon}.$FORMAT_ICON",
                    imageView)
            }
        }
    }
}