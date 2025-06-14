package com.x64tech.intune.respositories

import com.x64tech.intune.entites.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository: JpaRepository<ChatEntity, UUID> {
}