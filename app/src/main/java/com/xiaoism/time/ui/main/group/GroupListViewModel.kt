package com.xiaoism.time.ui.main.group

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.repository.GroupRepository
import com.xiaoism.time.repository.TimeDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val repository: GroupRepository) :
    ViewModel() {
    var groups: LiveData<List<GroupWithPersons>>

    init {
        groups = repository.allGroups
    }
}