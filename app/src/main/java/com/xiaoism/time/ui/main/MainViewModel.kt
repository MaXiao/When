package com.xiaoism.time.ui.main

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel @ViewModelInject constructor(application: Application) :
    AndroidViewModel(application) {
    //    private val data: MutableList<TimeEntity> =
//        mutableListOf(TimeEntity("test1"), TimeEntity("test2"))
    val times: LiveData<List<TimeEntity>>
    private val repository: TimeRepository

    init {
        repository = TimeRepository(TimeDatabase.getDatabase(application, viewModelScope).timeDao())
        times = repository.allTimes
    }

    fun addTime() {
        Log.d("main", "add time")
//        data.add(TimeEntity("text" + (0..10).random()))
//        times.notifyObserver()
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(TimeEntity("text" + (0..10).random()))
        }
    }
}