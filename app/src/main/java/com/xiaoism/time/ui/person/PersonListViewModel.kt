package com.xiaoism.time.ui.person

import androidx.lifecycle.*
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val repository: PersonRepository
) :
    ViewModel() {
    var persons: LiveData<List<PersonWithCity>> = repository.allPerson

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
}