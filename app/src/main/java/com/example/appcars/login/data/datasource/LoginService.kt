package com.example.appcars.login.data.datasource

import com.example.appcars.login.data.model.LoginRequest
import com.example.appcars.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}