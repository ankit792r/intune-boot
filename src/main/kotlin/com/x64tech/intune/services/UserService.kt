package com.x64tech.intune.services

import com.x64tech.intune.entites.UserEntity
import com.x64tech.intune.models.CreateUserDto
import com.x64tech.intune.models.UserView
import com.x64tech.intune.respositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {

    fun registerUser(createUserDto: CreateUserDto): UserView {
        val existingUser = userRepository.findByEmailOrUsername(createUserDto.email, createUserDto.username)
        if (existingUser.isPresent)
            throw Exception("User already exists")

        return userRepository.save(
            UserEntity(
                email = createUserDto.email,
                password = createUserDto.password,
                username = createUserDto.username,
                name = createUserDto.name
            )
        ).toView()
    }
}