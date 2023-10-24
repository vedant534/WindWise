package com.example.windwise.repository

import com.example.windwise.data.DataOrException
import com.example.windwise.model.Weather
import com.example.windwise.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api:WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String) : DataOrException<Weather,Boolean,Exception> {
        val response =try {
            api.getWeather(query = cityQuery, units = units)

        }catch(e:Exception){
            return DataOrException(e=e)

        }

        return DataOrException(data = response)

    }
}