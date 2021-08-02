package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.xiaoism.time.model.Group
import com.xiaoism.time.model.GroupPersonCrossRef
import com.xiaoism.time.model.GroupWithPersons

@Dao
interface GroupDao {
    @Transaction
    @Query("SELECT * FROM `group`")
    fun getAll(): LiveData<List<GroupWithPersons>>

    @Insert()
    fun create(group: Group): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroupAndPersonCrossRef(groupPersonCrossRef: GroupPersonCrossRef)

    @Transaction
    @Query("SELECT * FROM `group` WHERE groupId = :groupId")
    fun getGroup(groupId: Long): LiveData<GroupWithPersons>
}