package com.xiaoism.time.ui.main.group

import android.app.Person
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.GroupRepository
import com.xiaoism.time.ui.main.people.AddPersonViewModel
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
    private var persons = listOf<Person>()

    fun save() {
        if (name.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val groupId = repository.createGroup(name)
                Log.e("group", groupId.toString())
            }
        }
    }

    fun addMembers() {
        destination.value = Event(Destination.PERSON_LIST)
    }

    fun updateName(s: CharSequence, start: Int, before: Int, count: Int) {
        name = s.toString()
    }

    fun updateMembers(list: List<Person>) {
        persons = list
    }

    enum class Destination {
        PERSON_LIST
    }
}