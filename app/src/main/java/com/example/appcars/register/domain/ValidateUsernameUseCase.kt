// register/domain/ValidateUsernameUseCase.kt
package com.example.appcars.register.domain

import com.example.appcars.register.data.repository.RegisterRepository

class ValidateUsernameUseCase {
    private val repository = RegisterRepository()

    suspend operator fun invoke(username: String): Result<Boolean> {
        return try {
            if (username.length < 4) {
                return Result.failure(Exception("El nombre de usuario debe tener al menos 4 caracteres"))
            }

            repository.validateUsername(username)
                .map { response -> !response.exists }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}