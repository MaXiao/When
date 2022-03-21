package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import com.xiaoism.time.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao
) {
    val allCities: LiveData<List<City>> = cityDao.getAllCities();

    suspend fun searchCity(input: String): List<City> {
        return withContext(Dispatchers.IO) {
            cityDao.findCityWithName(input)
        }
    }
}