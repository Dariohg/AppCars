package com.example.appcars.register.data.model

import com.example.appcars.core.model.User

data class RegisterResponse(
    val message: String,
    val user: User
)