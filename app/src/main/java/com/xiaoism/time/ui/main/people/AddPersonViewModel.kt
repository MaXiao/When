package com.xiaoism.time.ui.main.people

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.repository.TimeDatabase
import com.xiaoism.time.util.livedata.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPersonViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, private val repository: PersonRepository
) :
    ViewModel() {
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
        CITY,
        SAVE_SUCCESS,
        SAVE_FAILED
    }
}