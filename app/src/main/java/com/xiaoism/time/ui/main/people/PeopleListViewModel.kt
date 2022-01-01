package com.xiaoism.time.ui.main.people

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PersonRepository
) :
    ViewModel() {
    var people: LiveData<List<PersonWithCity>>
    val all: LiveData<List<Person>> = repository.getAllItems()

    init {
        people = repository.allPerson
    }

    fun deletePerson(p: PersonWithCity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(p)
        }
    }

    fun addPerson(p: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.create(p);
        }
    }

    fun getAllItem(): LiveData<List<Person>> {
        return repository.getAllMember();
    }
}