package com.xiaoism.time.repository

import androidx.lifecycle.LiveData
import com.xiaoism.time.model.Group
import com.xiaoism.time.model.GroupPersonCrossRef
import com.xiaoism.time.model.GroupWithPersons
import javax.inject.Inject

class GroupRepository @Inject constructor(private val groupDao: GroupDao) {
    val allGroups: LiveData<List<GroupWithPersons>> = groupDao.getAll();

    fun createGroup(name: String): Long {
        return groupDao.create(Group(name = name))
    }

    fun addPersonToGroup(personId: Long, groupId: Long) {
        groupDao.insertGroupAndPersonCrossRef(GroupPersonCrossRef(groupId, personId))
    }

    fun getGroup(groupId: Long): LiveData<GroupWithPersons> {
        return groupDao.getGroup(groupId)
    }
}