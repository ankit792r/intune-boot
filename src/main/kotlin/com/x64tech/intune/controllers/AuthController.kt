package com.x64tech.intune.controllers

import com.x64tech.intune.utils.ApiResponse
import com.x64tech.intune.models.LoginDto
import com.x64tech.intune.models.LoginResponse
import com.x64tech.intune.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<ApiResponse<LoginResponse>> {
        val res = authService.login(loginDto)
        return ResponseEntity(ApiResponse(success = true, data = res), HttpStatus.OK)
    }
}