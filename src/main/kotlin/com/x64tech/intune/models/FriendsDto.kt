package com.x64tech.intune.models

import java.util.UUID

data class FriendStatusUpdateDto(
    val friendId: UUID,
    val status: String
)

data class FriendRequestDto(
    val friendId: UUID?,
    val username: String?,
)