package com.xiaoism.time.ui.main

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.City
import com.xiaoism.time.model.TimeEntity
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.repository.TimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    val cities: MutableLiveData<List<City>> = MutableLiveData()
    private val database = TimeDatabase.getDatabase(application, viewModelScope);
    private val repository: TimeRepository =
        TimeRepository(database.timeDao(), database.cityDao(), database.groupDao)
    private val perRepo: PersonRepository = PersonRepository(database.peopleDao());

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            repository.createGroup("toronto")
//            repository.createGroup("beijing")
//            repository.addPersonToGroup(1, 1)
//            repository.addPersonToGroup(2, 1)
        }

//        repository.allGroups.observeForever(Observer { groups ->
//            groups?.let {
//                Log.d("all groups", it.toString())
//            }
//        })
    }

    fun searchCity(input: String) {
        if (input.length < 3) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.searchCity(input)
            Log.d("search", result.toString())
            cities.postValue(result)
        }
    }

}