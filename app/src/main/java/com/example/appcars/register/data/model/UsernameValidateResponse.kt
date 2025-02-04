package com.example.appcars.register.data.model

data class UsernameValidateResponse(
    val exists: Boolean,
    val message: String
)