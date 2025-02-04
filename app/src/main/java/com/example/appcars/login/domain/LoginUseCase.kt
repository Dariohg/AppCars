package com.example.appcars.login.domain

import com.example.appcars.login.data.model.LoginRequest
import com.example.appcars.login.data.model.LoginResponse
import com.example.appcars.login.data.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository = LoginRepository()
) {
    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        // Validaciones de negocio
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Username y password son requeridos"))
        }

        val request = LoginRequest(
            username = username,
            password = password
        )

        return repository.login(request)
    }
}