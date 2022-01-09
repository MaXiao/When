package com.xiaoism.time.ui.main.group

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.GroupRepository
import com.xiaoism.time.util.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private val repository: GroupRepository) :
    ViewModel() {
    val destination = MutableLiveData<Event<Destination>>()
    private var name: String = ""
    val persons = MutableLiveData<List<PersonWithCity>>()

    fun save() {
        if (name.isEmpty()) {
            return
        }
        persons.value?.let { list ->
            val members = list.map { p -> p.person }
            viewModelScope.launch(Dispatchers.IO) {
                repository.createGroupAndAddMembers(name, members)
            }
        }
    }

    fun addMembers() {
        destination.value = Event(Destination.PERSON_LIST)
    }

    fun updateName(s: CharSequence, start: Int, before: Int, count: Int) {
        name = s.toString()
    }

    fun updateMembers(list: List<PersonWithCity>) {
        persons.value = list
    }

    enum class Destination {
        PERSON_LIST
    }
}