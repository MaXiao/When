package com.xiaoism.time.ui.main.group

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.repository.TimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupListViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    lateinit var groups: LiveData<List<GroupWithPersons>>
    private val database = TimeDatabase.getDatabase(application, viewModelScope)
    private val repository =
        TimeRepository(database.timeDao(), database.cityDao(), database.groupDao)

    init {
        groups = repository.allGroups
    }

    fun addGroup() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("group", "add group")
            repository.createGroup("toronto")
            repository.addPersonToGroup(1, 1)
            repository.addPersonToGroup(2, 1)
        }
    }
}