package com.xiaoism.time.ui.person

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xiaoism.time.model.PersonWithCity
import com.xiaoism.time.repository.PersonRepository
import com.xiaoism.time.ui.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonSelectionViewModel @Inject constructor(private val repository: PersonRepository) :
    ViewModel() {
    var people: LiveData<List<PersonWithCity>>
    val selection = mutableStateListOf<PersonWithCity>()
    var multiChoice: Boolean = false

    init {
        people = repository.allPerson
    }

    fun toggleSelection(p: PersonWithCity) {
        if (selection.contains(p)) {
            selection.remove(p)
        } else {
            selection.add(p)
        }
    }
}