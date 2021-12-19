package com.xiaoism.time.ui.main.people

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.util.livedata.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPersonViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    private val database = TimeDatabase.getDatabase(application, viewModelScope)
    private val repository = PersonRepository(database.peopleDao)
    val destination = MutableLiveData<Event<Destination>>()
    val cityName = MutableLiveData<String>()
    private var _city: City? = null

    fun chooseCity() {
        destination.value = Event(Destination.CITY)
    }

    fun confirm(name: String) {
        _city?.let {
            viewModelScope.launch(Dispatchers.IO) {
                repository.create(
                    Person(
                        name = name,
                        cityId = it.geonameId
                    )
                )
            }
        }
    }

    fun updateCity(city: City?) {
        city?.let {
            cityName.value = it.name
            _city = it
        }
    }

    enum class Destination {
        CITY
    }
}