package com.example.windwise.screens



import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.windwise.model.Unit
import com.example.windwise.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository)
    : ViewModel(){
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            repository
                .getUnits()
                .distinctUntilChanged()
                .collect{listOfUnit->
                    if(listOfUnit.isNullOrEmpty()){
                        Log.d("TAGQ",": EMPTY")
                    }
                    else{
                        _unitList.value = listOfUnit

                    }

                }
        }
    }

    fun insertUnit(unit: Unit)
            = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    fun updateUnit(unit: Unit)
            =  viewModelScope.launch {
        repository.updateUnit(unit)
    }

    fun deleteUnit(unit: Unit)
            = viewModelScope.launch {
        repository.deleteUnit(unit)
    }

    fun deleteAllUnit()
            = viewModelScope.launch {
        repository.deleteAllUnits()
    }

}