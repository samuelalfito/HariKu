package com.hariku.feature_pin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hariku.feature_pin.data.local.entity.Pin
import com.hariku.feature_pin.domain.repository.PinRepository
import kotlinx.coroutines.launch

class PinScreenViewModel(
    private val repository: PinRepository
) : ViewModel() {

//    private val _pin = MutableLiveData<Pin>()
    val pin: LiveData<Pin?> = repository.getPin().asLiveData()

//    fun loadPin() {
//        viewModelScope.launch {
//            // Asumsikan repository.getPin() adalah suspend function
//            val result = repository.getPin()
//            _pin.value = result // Perbarui LiveData dengan hasil dari repository
//        }
//    }

    fun addPin(pin: Pin){
        viewModelScope.launch {
            repository.insertPin(pin)
            Log.d("PinScreenViewModel", "Pin added: $pin")
        }
    }

    fun updatePin(pin: Pin){
        viewModelScope.launch {
            repository.updatePin(pin)
        }
    }

    fun deletePin(pin: Pin){
        viewModelScope.launch {
            repository.deletePin(pin)
        }
    }
}

class PinViewModelFactory(private val repository: PinRepository) :
    androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PinScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PinScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}