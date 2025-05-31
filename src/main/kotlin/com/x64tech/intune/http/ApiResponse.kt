package com.x64tech.intune.http

data class ApiResponse<T>(
    val success: Boolean = true,
    val message: String? = null,
    val error: Any? = null,
    val data: T? = null,
)
