package com.xiaoism.time.ui.main

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TimeDao {
    @Query("SELECT * from time_table")
    fun getAllTimes(): LiveData<List<TimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(time: TimeEntity)

    @Query("DELETE from time_table")
    suspend fun deleteAll()
}