package com.xiaoism.time.ui

import android.util.Log
import androidx.lifecycle.*
import com.xiaoism.time.model.City
import com.xiaoism.time.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val repository: CityRepository
) :
    ViewModel() {
    val cities: MutableLiveData<List<City>> = MutableLiveData()

    fun searchCity(input: String) {
        if (input.length < 3) {
            return
        }
        viewModelScope.launch {
            val result = repository.searchCity(input)
            cities.value = result
        }
    }

}