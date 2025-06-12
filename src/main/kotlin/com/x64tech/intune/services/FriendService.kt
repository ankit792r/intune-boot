package com.x64tech.intune.services

import com.x64tech.intune.entites.FriendEntity
import com.x64tech.intune.models.FriendStatusUpdateDto
import com.x64tech.intune.models.FriendView
import com.x64tech.intune.respositories.FriendRepository
import com.x64tech.intune.respositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FriendService @Autowired constructor(
    private val friendRepository : FriendRepository,
    private val userRepository : UserRepository,
) {
    fun listFriends(userId: UUID): List<FriendView> {
        val result = friendRepository.getUsersFriends(userId)
        return result.map { friend -> friend.toView() }
    }

    fun listFriendRequests(userId: UUID): List<FriendView> {
        val result = friendRepository.getFriendRequests(userId)
        return result.map { friend -> friend.toView() }
    }

    fun createFriendRequestWithUsername(userId: UUID, friendUsername: String): FriendView {
        val existingUser = userRepository.findByUsername(friendUsername)
        if (!existingUser.isPresent)
            throw Exception("User does not exist: $friendUsername")
        return createFriendRequest(userId, existingUser.get().id as UUID)
    }

    fun createFriendRequest(userId: UUID, friendId: UUID): FriendView {
        val existingFriendRequest = friendRepository.getFriendRequestSentAlready(userId, friendId)
        if (existingFriendRequest.isPresent)
            throw Exception("friend already sent")

        val user = userRepository.findById(userId)
        val acceptor = userRepository.findById(friendId)

        if (user.isPresent && acceptor.isPresent)
            return friendRepository.save(
                FriendEntity(null, "REQUEST", user.get(), acceptor.get())
            ).toView()

        throw Exception("sender or acceptor does not exist")
    }

    fun updateFriendStatus(friendStatusUpdateDto: FriendStatusUpdateDto): FriendView {
        val friendRequests = friendRepository.findById(friendStatusUpdateDto.friendId)
        if (!friendRequests.isPresent)
            throw Exception("friend does not exist")

        return friendRepository.save(
            friendRequests.get().copy(status = friendStatusUpdateDto.status)
        ).toView()
    }

    fun deleteFriend(friendId: UUID) {
        friendRepository.deleteById(friendId)
    }
}