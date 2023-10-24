package com.example.windwise.di

import android.content.Context
import androidx.room.Room
import com.example.windwise.data.WeatherDao
import com.example.windwise.data.WeatherDatabase
import com.example.windwise.network.WeatherApi
import com.example.windwise.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase):WeatherDao
        = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase
        = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}