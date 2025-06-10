package com.x64tech.intune.services

import com.x64tech.intune.respositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DetailService @Autowired constructor(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(userId: String?): UserDetails {
        val optionalUser = userRepository.findById(UUID.fromString(userId))
        if (optionalUser.isPresent)
            return optionalUser.get().toUserDetails()
        throw UsernameNotFoundException("User $userId not found")
    }
}