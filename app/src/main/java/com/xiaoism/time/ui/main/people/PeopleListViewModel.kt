package com.xiaoism.time.ui.main.people

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeopleListViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    lateinit var people: LiveData<List<PersonWithCity>>
    private val database = TimeDatabase.getDatabase(application, viewModelScope)
    private val repository = PersonRepository(database.peopleDao())

    init {
        people = repository.allPerson
    }
}