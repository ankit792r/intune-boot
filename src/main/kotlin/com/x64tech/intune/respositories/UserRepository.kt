package com.x64tech.intune.respositories

import com.x64tech.intune.entites.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<UserEntity, UUID> {
    fun findByUsername(username: String): Optional<UserEntity>
    fun findByEmail(email: String): Optional<UserEntity>
    fun findByEmailOrUsername(email: String, username: String): Optional<UserEntity>
}