package com.xiaoism.time.ui.main.city

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xiaoism.time.databinding.CityCellItemBinding
import com.xiaoism.time.model.City

interface OnCityClickListener {
    fun onItemClick(city: City)
}

class CityAdapter(context: Context, val listener: OnCityClickListener) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cities = emptyList<City>()

    inner class CityViewHolder(val binding: CityCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City, listener: OnCityClickListener) {
            binding.item = city
            binding.textview.setOnClickListener {
                listener.onItemClick(city)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityCellItemBinding.inflate(inflater, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cities[position], listener)
    }

    override fun getItemCount(): Int = cities.size

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }
}