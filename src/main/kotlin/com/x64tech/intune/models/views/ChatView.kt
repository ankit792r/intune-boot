package com.x64tech.intune.models.views

import java.util.*

data class ChatView(
    val id: UUID?,
    val name: String?,
    val image: String?,
    val type: String,
    val members: List<FriendView>
)