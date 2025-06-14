package com.x64tech.intune.services

import com.x64tech.intune.entites.ChatEntity
import com.x64tech.intune.respositories.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService @Autowired constructor(
    private val chatRepository: ChatRepository
) {
}