package com.x64tech.intune.controllers

import com.x64tech.intune.http.ApiResponse
import com.x64tech.intune.models.CreateUserDto
import com.x64tech.intune.models.UserView
import com.x64tech.intune.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody createUserDto: CreateUserDto): ResponseEntity<ApiResponse<UserView>> {
        val user = userService.registerUser(createUserDto)
        return ResponseEntity(ApiResponse(message = "User created", data = user), HttpStatus.CREATED)
    }
}