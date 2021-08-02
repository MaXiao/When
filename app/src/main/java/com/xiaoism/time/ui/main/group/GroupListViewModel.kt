package com.xiaoism.time.ui.main.group

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.repository.TimeRepository

class GroupListViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    lateinit var groups: LiveData<List<GroupWithPersons>>
    private val database = TimeDatabase.getDatabase(application, viewModelScope)
    private val repository =
        TimeRepository(database.timeDao(), database.cityDao(), database.groupDao)

    init {
        groups = repository.allGroups
    }
}