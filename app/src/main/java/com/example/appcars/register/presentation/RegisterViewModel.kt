package com.example.appcars.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcars.register.data.model.RegisterRequest
import com.example.appcars.register.domain.RegisterUserUseCase
import com.example.appcars.register.domain.ValidateUsernameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiState(
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val age: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
    val isUsernameValid: Boolean = false,
    val isRegistered: Boolean = false
)
class RegisterViewModel : ViewModel() {
    private val validateUsernameUseCase = ValidateUsernameUseCase()
    private val registerUserUseCase = RegisterUserUseCase()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onUsernameChanged(username: String) {
        _uiState.update { it.copy(username = username, isUsernameValid = false) }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun onAgeChanged(age: String) {
        _uiState.update { it.copy(age = age) }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun validateUsername() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            validateUsernameUseCase(_uiState.value.username)
                .onSuccess { isValid ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isUsernameValid = isValid
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }

    private fun clearFields() {
        _uiState.update {
            it.copy(
                name = "",
                username = "",
                email = "",
                phone = "",
                age = "",
                password = "",
                isUsernameValid = false
            )
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val ageInt = _uiState.value.age.toIntOrNull()
            if (ageInt == null) {
                _uiState.update { it.copy(isLoading = false, error = "La edad debe ser un número válido") }
                return@launch
            }

            val request = RegisterRequest(
                nombre = _uiState.value.name,
                username = _uiState.value.username,
                email = _uiState.value.email,
                telefono = _uiState.value.phone,
                edad = ageInt,
                password = _uiState.value.password
            )

            registerUserUseCase(request)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRegistered = true,
                            successMessage = "¡Registro exitoso! Redirigiendo al login..."
                        )
                    }
                    clearFields()
                }
                .onFailure { error ->
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