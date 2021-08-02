package com.xiaoism.time.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.xiaoism.time.model.*

class TimeRepository(
    private val timeDao: TimeDao,
    private val cityDao: CityDao,
    private val groupDao: GroupDao
) {
    val allTimes: LiveData<List<TimeEntity>> = timeDao.getAllTimes()
    val allCities: LiveData<List<City>> = cityDao.getAllCities();
    val allGroups: LiveData<List<GroupWithPersons>> = groupDao.getAll();

    suspend fun insert(time: TimeEntity) {
        timeDao.insert(time);
    }

    fun searchCity(input: String): List<City> {
        val result = cityDao.findCityWithName(input)
        return result;
    }

    fun createGroup(name: String) {
        groupDao.create(Group(name = name))
    }

    fun addPersonToGroup(personId: Long, groupId: Long) {
        groupDao.insertGroupAndPersonCrossRef(GroupPersonCrossRef(groupId, personId))
    }

    fun getGroup(groupId: Long): LiveData<GroupWithPersons> {
        return groupDao.getGroup(groupId)
    }
}