package com.x64tech.intune.models.views

import java.util.*


data class MessageView(
    val id: UUID,
    val content: String,
    val type: String,
    val createdAt: Date,
    val createdBy: FriendView,
    val chat: ChatView,
)
