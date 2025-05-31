package com.x64tech.intune.models

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String,
    val name: String?,
)

data class UpdateUserDto(
    val username: String,
    val name: String?,
)
