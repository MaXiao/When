package com.xiaoism.time.ui.main

import androidx.lifecycle.LiveData

class TimeRepository(private val timeDao: TimeDao) {
    val allTimes: LiveData<List<TimeEntity>> = timeDao.getAllTimes()

    suspend fun insert(time: TimeEntity) {
        timeDao.insert(time)
    }
}