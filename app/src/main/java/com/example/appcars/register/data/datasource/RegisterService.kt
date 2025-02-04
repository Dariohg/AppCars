package com.example.appcars.register.data.datasource

import com.example.appcars.register.data.model.RegisterRequest
import com.example.appcars.register.data.model.RegisterResponse
import com.example.appcars.register.data.model.UsernameValidateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterService {
    @GET("users/validate/{username}")
    suspend fun validateUsername(@Path("username") username: String): Response<UsernameValidateResponse>

    @POST("users/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}