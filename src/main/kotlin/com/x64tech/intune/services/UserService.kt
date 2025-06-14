package com.x64tech.intune.services

import com.x64tech.intune.entites.UserEntity
import com.x64tech.intune.models.*
import com.x64tech.intune.models.views.UserView
import com.x64tech.intune.respositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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
                name = createUserDto.name,
                bio = createUserDto.bio,
            )
        ).toView()
    }

    fun updateProfile(userId: UUID, userProfileUpdateDto: UserProfileUpdateDto): UserProfileUpdateDto {
        val userExists = userRepository.findById(userId)
        if (!userExists.isPresent)
            throw Exception("User not found")

        val updatedUser = userRepository.save(
            userExists.get().copy(username = userProfileUpdateDto.username)
        )

        return UserProfileUpdateDto(username = updatedUser.username, "")
    }

    fun updateBasic(userId: UUID, userBasicUpdateDto: UserBasicUpdateDto): UserBasicUpdateDto {
        val userExists = userRepository.findById(userId)
        if (!userExists.isPresent)
            throw Exception("User not found")

        val updatedUser = userRepository.save(
            userExists.get().copy(name = userBasicUpdateDto.name, bio = userBasicUpdateDto.bio)
        )

        return UserBasicUpdateDto(name = updatedUser.name ?: "", bio = updatedUser.bio)
    }

    fun updateEmail(userId: UUID, userEmailUpdateDto: UserEmailUpdateDto) {
        val userExists = userRepository.findById(userId)
        if (!userExists.isPresent)
            throw Exception("User not found")

        // todo: check for the otp
        userRepository.save(userExists.get().copy(email = userEmailUpdateDto.email))
    }

    fun emailUpdateRequest(userId: UUID) {
        val userExists = userRepository.findById(userId)
        if (!userExists.isPresent)
            throw Exception("User not found")

        // todo: generate otp and send it to user current email
    }
}