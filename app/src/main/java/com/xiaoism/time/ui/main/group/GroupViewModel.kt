package com.xiaoism.time.ui.main.group

import androidx.lifecycle.LiveData
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
    lateinit var group: LiveData<GroupWithPersons>

    fun addMember(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            group.value?.let { g ->
                repository.addPersonToGroup(person.personId, g.group.groupId)
            }
        }
    }

    fun configGroup(groupID: Long) {
        group = repository.getGroup(groupID)
    }
}