package com.xiaoism.time.ui.main.people

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.ui.main.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val repository: PersonRepository
) :
    ViewModel() {
    var people: LiveData<List<PersonWithCity>>
    val selection: MutableLiveData<MutableList<Int>> = MutableLiveData()

    init {
        people = repository.allPerson
        selection.value = ArrayList()
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

    fun toggleSelection(index: Int) {
        selection.value?.let { list ->
            if (list.contains(index)) {
                list.remove(index)
            } else {
                list.add(index)
            }
        }
        selection.notifyObserver()
    }
}