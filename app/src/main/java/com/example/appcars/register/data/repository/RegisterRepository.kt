package com.example.appcars.register.data.repository

import com.example.appcars.core.network.RetrofitHelper
import com.example.appcars.register.data.model.RegisterRequest
import com.example.appcars.register.data.model.RegisterResponse
import com.example.appcars.register.data.model.UsernameValidateResponse

class RegisterRepository {
    private val registerService = RetrofitHelper.registerService

    suspend fun validateUsername(username: String): Result<UsernameValidateResponse> {
        return try {
            val response = registerService.validateUsername(username)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = registerService.register(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}