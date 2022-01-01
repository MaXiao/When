package com.xiaoism.time.ui.main

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xiaoism.time.model.City
import com.xiaoism.time.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CityRepository
) :
    ViewModel() {
    val cities: MutableLiveData<List<City>> = MutableLiveData()

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