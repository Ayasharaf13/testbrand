package com.example.testapi.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapi.home.model.InterfaceBrands
import com.example.testapi.models.brands.ApiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
class HomeViewModel {
}

 */

class HomeViewModel(private val remoteSource: InterfaceBrands) : ViewModel() {

    private val _brandsList: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val accessBrandsList: StateFlow<ApiState> get() = _brandsList

    fun getBrands() {
        viewModelScope.launch {
            try {
                val brands = withContext(Dispatchers.IO) {
                    remoteSource.getBrands()
                }
                _brandsList.value = ApiState.Success(brands)
            } catch (error: Throwable) {
                _brandsList.value = ApiState.Failure(error)
            }
        }
    }
}
