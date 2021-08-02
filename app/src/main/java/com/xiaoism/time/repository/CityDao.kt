package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xiaoism.time.model.City

@Dao
interface CityDao {
    @Query("SELECT * from cities")
    fun getAllCities(): LiveData<List<City>>

    fun findCityWithName(search: String): List<City> {
        return findCityWithNameQuery("%$search%")
    }

    @Query(
        "SELECT * FROM cities WHERE Name LIKE :search " +
                "OR `ASCII Name` LIKE :search"
    )
    fun findCityWithNameQuery(search: String): List<City>
}