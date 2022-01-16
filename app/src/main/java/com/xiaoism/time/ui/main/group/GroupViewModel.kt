package com.xiaoism.time.ui.main.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.Group
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.Person
import com.xiaoism.time.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val repository: GroupRepository) :
    ViewModel() {
    private lateinit var group: GroupWithPersons

    fun addMember(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPersonToGroup(person.personId, group.group.groupId)
        }
    }

    fun setGroup(group: GroupWithPersons) {
        this.group = group
    }
}