package com.xiaoism.time.ui.main.group

import android.app.Person
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xiaoism.time.repository.GroupRepository
import com.xiaoism.time.ui.main.people.AddPersonViewModel
import com.xiaoism.time.util.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(private val repository: GroupRepository) : ViewModel() {
    val destination = MutableLiveData<Event<CreateGroupViewModel.Destination>>()
    private var name: String = ""
    val persons = listOf<Person>()

    fun save() {
        if (name.isNotEmpty()) {
            val groupId = repository.createGroup(name)
            Log.e("group", groupId.toString())
        }
    }

    fun addMembers() {
        destination.value = Event(Destination.PERSON_LIST)
    }

    fun updateName(n: String) {
        name = n
    }

    enum class Destination {
        PERSON_LIST
    }
}