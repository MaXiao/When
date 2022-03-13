package com.xiaoism.time.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val repository: GroupRepository) :
    ViewModel() {
    var groups: LiveData<List<GroupWithPersons>>

    init {
        groups = repository.allGroups
    }
}