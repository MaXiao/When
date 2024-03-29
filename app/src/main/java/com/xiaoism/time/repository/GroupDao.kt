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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroupAndPersonCrossRef(groupPersonCrossRef: GroupPersonCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGroupAndPersonCrossRefs(refs: List<GroupPersonCrossRef>)

    @Delete
    fun removeGroupAndPersonCrossRef(groupPersonCrossRef: GroupPersonCrossRef)

    @Transaction
    @Query("SELECT * FROM `group` WHERE groupId = :groupId")
    fun getGroup(groupId: Long): LiveData<GroupWithPersons>

    @Update
    fun update(group: Group)
}