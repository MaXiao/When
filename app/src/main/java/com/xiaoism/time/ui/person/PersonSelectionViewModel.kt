package com.xiaoism.time.ui.person

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
    val selection: MutableLiveData<MutableList<PersonWithCity>> = MutableLiveData()
    var multiChoice: Boolean = false

    init {
        people = repository.allPerson
        selection.value = ArrayList()
    }

    fun toggleSelection(p: PersonWithCity) {
        selection.value?.let { list ->
            if (list.contains(p)) {
                list.remove(p)
            } else {
                list.add(p)
            }
        }
        selection.notifyObserver()
    }
}