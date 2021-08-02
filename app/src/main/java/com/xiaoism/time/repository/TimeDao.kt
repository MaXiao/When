package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xiaoism.time.model.TimeEntity

@Dao
interface TimeDao {
    @Query("SELECT * from time")
    fun getAllTimes(): LiveData<List<TimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(time: TimeEntity)

    @Query("DELETE from time")
    suspend fun deleteAll()
}