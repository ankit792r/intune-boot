package com.x64tech.intune.entites

import com.x64tech.intune.models.views.MessageView
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "messages")
data class MessageEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(name = "content", nullable = false)
    val content: String,

    @Column(name="type", nullable = false, length = 20)
    val type: String,

    @Column(name="created_at", nullable = false)
    val createdAt: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    val createdBy: FriendEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    val chat: ChatEntity,
) {
    fun toView(): MessageView = MessageView(id!!, content, type, createdAt, createdBy.toView(), chat.toView())
}
