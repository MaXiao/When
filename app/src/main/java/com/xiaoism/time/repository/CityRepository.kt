package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import com.xiaoism.time.model.*
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao
) {
    val allCities: LiveData<List<City>> = cityDao.getAllCities();

    fun searchCity(input: String): List<City> {
        val result = cityDao.findCityWithName(input)
        return result;
    }
}