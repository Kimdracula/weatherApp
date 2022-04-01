package com.homework.weatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.weatherapp.databinding.RecycleItemBinding

class MfAdapter(private val citiesList: List<String>):
    RecyclerView.Adapter<MfAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = RecycleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
with(holder){
    with(citiesList[position]){
binding.textViewCityName.text
    }
}
    }

    override fun getItemCount(): Int = citiesList.size

   inner class ViewHolder(val binding: RecycleItemBinding): RecyclerView.ViewHolder(binding.root){

    }

}