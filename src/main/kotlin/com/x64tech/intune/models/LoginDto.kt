package com.x64tech.intune.models

data class LoginDto(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val token: String,
    val user: UserView
)