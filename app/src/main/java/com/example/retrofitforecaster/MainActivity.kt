package com.example.retrofitforecaster

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitforecaster.adapter.WeatherAdapter
import com.example.retrofitforecaster.network.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private val weatherAdapter = WeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установить макет
        setContentView(R.layout.activity_main)

        // Настройка Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Настройка RecyclerView
        recyclerView = findViewById(R.id.r_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = weatherAdapter

        // Загрузка данных погоды
        loadWeatherData()
    }

    private fun loadWeatherData() {
        val apiKey = "24b9f8c0d021b967df6b0369c4d40860"
        lifecycleScope.launch {
            try {
                Log.d("MainActivity", "Запрос погоды для города: Шклов")
                val response = apiService.getWeatherForecast("Шклов", apiKey)

                if (response.list.isNotEmpty()) {
                    Log.d("MainActivity", "Получены данные погоды: ${response.list.size} записей")
                    weatherAdapter.submitList(response.list)
                } else {
                    Log.w("MainActivity", "Ответ от сервера пустой")
                    showToast("Нет данных")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка при загрузке данных: ${e.message}", e)
                showToast("Ошибка при загрузке данных")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}