package com.x64tech.intune.controllers

import com.x64tech.intune.models.*
import com.x64tech.intune.utils.ApiResponse
import com.x64tech.intune.services.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestBody createUserDto: CreateUserDto
    ): ResponseEntity<ApiResponse<UserView>> {
        val user = userService.registerUser(createUserDto)
        return ResponseEntity(ApiResponse(message = "User created", data = user), HttpStatus.CREATED)
    }

    @PutMapping("/profile")
    fun updateProfile(
        request: HttpServletRequest,
        @RequestBody userProfileUpdateDto: UserProfileUpdateDto
    ): ResponseEntity<ApiResponse<UserProfileUpdateDto>> {
        val userId = request.getAttribute("userId") as UUID
        val updatedUser = userService.updateProfile(userId, userProfileUpdateDto)
        return ResponseEntity(ApiResponse(message = "User profile updated", data = updatedUser), HttpStatus.OK)
    }

    @PutMapping("/basic")
    fun updateBasic(
        request: HttpServletRequest,
        @RequestBody userBasicUpdateDto: UserBasicUpdateDto
    ): ResponseEntity<ApiResponse<UserBasicUpdateDto>> {
        val userId = request.getAttribute("userId") as UUID
        val updatedUser = userService.updateBasic(userId, userBasicUpdateDto)
        return ResponseEntity(ApiResponse(message = "User basic details updated", data = updatedUser), HttpStatus.OK)
    }

    @PostMapping("/email-update")
    fun updateEmail(
        request: HttpServletRequest,
        @RequestBody userEmailUpdateDto: UserEmailUpdateDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        val userId = request.getAttribute("userId") as UUID
        userService.updateEmail(userId, userEmailUpdateDto)
        return ResponseEntity(ApiResponse(message = "User email updated"), HttpStatus.NO_CONTENT)
    }

    @GetMapping("/email-update-request")
    fun emailUpdateRequest(
        request: HttpServletRequest,
    ): ResponseEntity<ApiResponse<Nothing>> {
        val userId = request.getAttribute("userId") as UUID
        userService.emailUpdateRequest(userId)
        return ResponseEntity(ApiResponse(message = "User email update request processed"), HttpStatus.NO_CONTENT)
    }
}