package com.example.appcars.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcars.home.data.model.CreateHotWheelsRequest
import com.example.appcars.home.data.model.HotWheelsCar
import com.example.appcars.home.domain.AddHotWheelsUseCase
import com.example.appcars.home.domain.GetHotWheelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val cars: List<HotWheelsCar> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val getHotWheelsUseCase = GetHotWheelsUseCase()
    private val addHotWheelsUseCase = AddHotWheelsUseCase()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCars()
    }

    private fun loadCars() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getHotWheelsUseCase()
                .onSuccess { cars ->
                    println("DEBUG: Success - Cars received: ${cars.size}")
                    println("DEBUG: First car URLs: ${cars.firstOrNull()?.imagenes}")
                    _uiState.update {
                        it.copy(
                            cars = cars,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    println("DEBUG: Error loading cars: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    fun addCar(request: CreateHotWheelsRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            addHotWheelsUseCase(request)
                .onSuccess { car ->
                    println("Éxito al agregar carro: $car")
                    _uiState.update { state ->
                        state.copy(
                            cars = state.cars + car,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    println("Error al agregar carro: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }
}