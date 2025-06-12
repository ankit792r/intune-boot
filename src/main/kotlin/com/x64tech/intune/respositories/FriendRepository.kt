package com.x64tech.intune.respositories

import com.x64tech.intune.entites.FriendEntity
import com.x64tech.intune.models.FriendView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface FriendRepository: JpaRepository<FriendEntity, UUID> {
    @Query("select f from FriendEntity f where f.initiator = :userId or f.acceptor = :userId")
    fun getUsersFriends(userId: UUID): List<FriendEntity>

    @Query("select f from FriendEntity f where f.initiator = :userId and f.acceptor = :friendId")
    fun getFriendRequestSentAlready(userId: UUID, friendId: UUID): Optional<FriendEntity>

    @Query("select f from FriendEntity f where f.initiator = :userId or f.acceptor = :userId and f.status in ('SENT', 'RECEIVE')")
    fun getFriendRequests(userId: UUID): List<FriendEntity>
}