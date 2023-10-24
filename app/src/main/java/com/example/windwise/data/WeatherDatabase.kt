package com.example.windwise.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.windwise.model.Favorite
import com.example.windwise.model.Unit


@Database(entities = [Favorite::class, Unit::class],version=2, exportSchema = false)
abstract class WeatherDatabase :RoomDatabase(){
    abstract fun weatherDao():WeatherDao


}