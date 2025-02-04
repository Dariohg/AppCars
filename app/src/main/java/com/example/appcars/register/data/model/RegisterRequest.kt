package com.example.appcars.register.data.model

data class RegisterRequest(
    val nombre: String,
    val username: String,
    val email: String,
    val telefono: String,
    val edad: Int,
    val password: String
)