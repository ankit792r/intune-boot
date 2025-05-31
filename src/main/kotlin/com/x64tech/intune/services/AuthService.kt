package com.x64tech.intune.services

import com.x64tech.intune.models.LoginDto
import com.x64tech.intune.respositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
) {
    fun login(loginDto: LoginDto): String {
        val userExists = userRepository.findByEmail(loginDto.email)
        if (!userExists.isPresent)
            throw Exception("User not found with email: ${loginDto.email}")

        val user = userExists.get()
        if (user.password != loginDto.password)
            throw Exception("Wrong password")

        return jwtService.generateToken(user.username)
    }
}