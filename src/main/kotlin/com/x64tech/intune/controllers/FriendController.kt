package com.x64tech.intune.controllers

import com.x64tech.intune.models.FriendRequestDto
import com.x64tech.intune.models.FriendStatusUpdateDto
import com.x64tech.intune.models.FriendView
import com.x64tech.intune.services.FriendService
import com.x64tech.intune.utils.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController(value = "friends")
class FriendController @Autowired constructor(
    private val friendService: FriendService,
) {
    @GetMapping("list")
    fun listFriends(
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<List<FriendView>>> {
        val userId = request.getAttribute("userId") as UUID
        val friends = friendService.listFriends(userId)
        return ResponseEntity(ApiResponse(message = "friend list", data = friends), HttpStatus.OK)
    }

    @GetMapping("list-requests")
    fun getFriendRequests(
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<List<FriendView>>> {
        val userId = request.getAttribute("userId") as UUID
        val res = friendService.listFriendRequests(userId)
        return ResponseEntity(ApiResponse(message = "friend requests list", data = res), HttpStatus.OK)
    }


    @PostMapping("new-request")
    fun createFriendRequest(
        request: HttpServletRequest,
        @RequestBody friendRequestDto: FriendRequestDto,
    ): ResponseEntity<ApiResponse<FriendView>> {
        if (friendRequestDto.friendId?.equals("") == true || friendRequestDto.username == "")
            throw Exception("Username and friendId both cannot be empty, at least one is required")

        val userId = request.getAttribute("userId") as UUID
        val result: FriendView = if (friendRequestDto.friendId?.equals("") != true)
            friendService.createFriendRequest(userId, friendRequestDto.friendId as UUID)
        else friendService.createFriendRequestWithUsername(userId, friendRequestDto.username as String)

        return ResponseEntity(ApiResponse(message = "friend request created", data = result), HttpStatus.CREATED)
    }

    @PostMapping("update-status")
    fun updateFriendStatus(
        request: HttpServletRequest,
        @RequestBody friendStatusUpdateDto: FriendStatusUpdateDto
    ): ResponseEntity<ApiResponse<FriendView>> {
        val result = friendService.updateFriendStatus(friendStatusUpdateDto)
        return ResponseEntity(ApiResponse(message = "friend status updated", data = result), HttpStatus.OK)
    }

    @DeleteMapping("delete/{friendId}")
    fun deleteFriend(
        @PathVariable("friendId") friendId: String
    ): ResponseEntity<ApiResponse<Nothing>> {
        friendService.deleteFriend(UUID.fromString(friendId))
        return ResponseEntity(ApiResponse(success = true, message = "friend deleted"), HttpStatus.NO_CONTENT)
    }
}