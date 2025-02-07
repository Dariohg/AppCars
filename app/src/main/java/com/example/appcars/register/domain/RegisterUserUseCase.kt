package com.example.appcars.register.domain

import com.example.appcars.register.data.model.RegisterRequest
import com.example.appcars.register.data.repository.RegisterRepository

class RegisterUserUseCase {
    private val repository = RegisterRepository()

    suspend operator fun invoke(request: RegisterRequest): Result<Unit> {
        return try {
            if (!validateRequest(request)) {
                return Result.failure(Exception("Por favor verifica todos los campos"))
            }

            repository.register(request)
                .map { }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateRequest(request: RegisterRequest): Boolean {
        return request.nombre.isNotBlank() &&
                request.username.length >= 4 &&
                request.email.contains("@") &&
                request.telefono.length >= 8 &&
                request.edad >= 18 &&
                request.password.length >= 6
    }
}