package com.example.retrofitforecaster.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitforecaster.R
import com.example.retrofitforecaster.model.WeatherItem
import com.bumptech.glide.Glide
import android.widget.ImageView

class WeatherAdapter : ListAdapter<WeatherItem, RecyclerView.ViewHolder>(DiffCallback()) {

    // Типы ViewHolder
    private companion object {
        private const val ITEM_COLD = 123
        private const val ITEM_HOT = 321
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM_COLD -> {
                val view = inflater.inflate(R.layout.item_weather_cold, parent, false)
                WeatherViewHolderCold(view)
            }
            ITEM_HOT -> {
                val view = inflater.inflate(R.layout.item_weather_hot, parent, false)
                WeatherViewHolderHot(view)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).main.temp < 0) {
            ITEM_COLD
        } else {
            ITEM_HOT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weatherItem = getItem(position)
        when (holder) {
            is WeatherViewHolderCold -> holder.bind(weatherItem)
            is WeatherViewHolderHot -> holder.bind(weatherItem)
        }
    }

    class WeatherViewHolderCold(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherItem: WeatherItem) {
            itemView.findViewById<TextView>(R.id.date_text).text = weatherItem.dt_txt
            itemView.findViewById<TextView>(R.id.temp_text).text = "${weatherItem.main.temp} °C"

            val iconUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
            val imageView = itemView.findViewById<ImageView>(R.id.weather_icon)
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(imageView)
        }
    }

    class WeatherViewHolderHot(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherItem: WeatherItem) {
            itemView.findViewById<TextView>(R.id.date_text).text = weatherItem.dt_txt
            itemView.findViewById<TextView>(R.id.temp_text).text = "${weatherItem.main.temp} °C"

            val iconUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
            val imageView = itemView.findViewById<ImageView>(R.id.weather_icon)
            Glide.with(itemView.context)
                .load(iconUrl)
                .into(imageView)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem.dt_txt == newItem.dt_txt
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem == newItem
        }
    }
}