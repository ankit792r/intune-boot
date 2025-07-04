package com.x64tech.intune.models.views

import java.util.*

data class FriendView(
    val id : UUID,
    val status : String,
    val initiator : UserView,
    val acceptor : UserView,
)