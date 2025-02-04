package com.example.appcars.login.data.model

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: String,
    val nombre: String,
    val username: String,
    val email: String,
    val telefono: String,
    val edad: Int
)