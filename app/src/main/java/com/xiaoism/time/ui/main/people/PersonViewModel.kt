package com.xiaoism.time.ui.main.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xiaoism.time.model.City
import com.xiaoism.time.model.Person
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: PersonRepository) : ViewModel() {
    lateinit var person: LiveData<PersonWithCity>

    fun configPerson(personId: Long) {
        person = repo.getPerson(personId)
    }

    fun updateCity(city: City?) {
        city?.let { city ->
            person.value?.let { personWithCity ->
                viewModelScope.launch(Dispatchers.IO) {
                    repo.updateCity(personWithCity.person, city)
                }
            }
        }
    }

    fun updateName(name: String) {
        person.value?.let { personWithCity ->
            viewModelScope.launch {
                repo.updateName(personWithCity.person, name)
            }
        }
    }
}