package com.example.windwise.repository

import com.example.windwise.data.WeatherDao
import com.example.windwise.model.Favorite
import com.example.windwise.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherDbRepository @Inject constructor(private val weatherDao:WeatherDao){

    fun getFavorite(): Flow<List<Favorite>> = weatherDao.getFavorite()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorite()
    suspend fun getFavById(city:String) :Favorite = weatherDao.getFavById(city)


    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}