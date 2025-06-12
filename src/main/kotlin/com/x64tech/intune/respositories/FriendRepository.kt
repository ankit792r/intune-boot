package com.x64tech.intune.respositories

import com.x64tech.intune.entites.FriendEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface FriendRepository: JpaRepository<FriendEntity, UUID> {
    @Query("select f from FriendEntity f where f.status not like 'REQUEST' and (f.initiator.id = :userId or f.acceptor.id = :userId) ")
    fun getUsersFriends(userId: UUID): List<FriendEntity>

    @Query("select f from FriendEntity f where f.initiator.id = :userId and f.acceptor.id = :friendId")
    fun getFriendRequestSentAlready(userId: UUID, friendId: UUID): Optional<FriendEntity>

    @Query("select f from FriendEntity f where f.initiator.id = :userId or f.acceptor.id = :userId and f.status = 'REQUEST' ")
    fun getFriendRequests(userId: UUID): List<FriendEntity>
}