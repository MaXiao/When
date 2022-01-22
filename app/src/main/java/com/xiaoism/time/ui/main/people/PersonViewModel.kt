package com.xiaoism.time.ui.main.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: PersonRepository) : ViewModel() {
    lateinit var person: LiveData<PersonWithCity>

    fun configPerson(personId: Long) {
        person = repo.getPerson(personId)
    }
}