package com.x64tech.intune.models

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String,
    val name: String?,
    val bio: String?,
)

data class UpdateUserDto(
    val username: String,
    val name: String?,
)

data class UserProfileUpdateDto(
    val username: String,
    val pic: String?,
)

data class UserBasicUpdateDto(
    val name: String,
    val bio: String?,
)

data class UserEmailUpdateDto(
    val email: String,
    val otp: String,
)