package com.xiaoism.time.ui.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.Group
import com.xiaoism.time.model.GroupWithPersons
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.GroupRepository
import com.xiaoism.time.util.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private val repository: GroupRepository) :
    ViewModel() {
    val destination = MutableLiveData<Event<Destination>>()
    val name = MutableLiveData("")
    val persons = MutableLiveData<List<PersonWithCity>>()
    var group: LiveData<GroupWithPersons>? = null

    fun save() {
        name.value?.let { name ->
            group?.value?.let {
                val newGroup = Group(groupId = it.group.groupId, name = name)
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.update(newGroup)

                    }
                    destination.value = Event(Destination.UPDATE_DONE)
                }
            } ?: run {
                persons.value?.let { list ->
                    val members = list.map { p -> p.person }
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.createGroupAndAddMembers(name, members)
                    }
                    destination.value = Event(Destination.UPDATE_DONE)
                }
            }
        }
    }

    fun addMembers() {
        destination.value = Event(Destination.PERSON_LIST)
    }

    fun removeMember(person: PersonWithCity) {
        group?.value?.let { g ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.removePersonFromGroup(person.person.personId, g.group.groupId)
            }
        } ?: run {
            persons.value =
                persons.value?.filter { p -> p.person.personId == person.person.personId }
        }
    }

    fun updateName(input: String) {
        name.value = input
    }

    fun updateMembers(list: List<PersonWithCity>) {
        persons.value = list
    }

    fun configGroup(groupId: Long) {
        group = repository.getGroup(groupId)
    }

    fun updateData(group: GroupWithPersons?) {
        group?.let {
            name.value = it.group.name
            persons.value = it.persons
        }
    }

    enum class Destination {
        PERSON_LIST,
        UPDATE_DONE
    }
}