package com.x64tech.intune.models.views

import java.util.*

data class UserView(
    val id: UUID,
    val username: String,
    val name: String?,
    val email: String,
    val bio: String?,
)
