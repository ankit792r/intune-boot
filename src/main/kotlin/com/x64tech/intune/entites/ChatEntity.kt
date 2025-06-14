package com.x64tech.intune.entites

import com.x64tech.intune.models.views.ChatView
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "chats")
data class ChatEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(length = 50)
    val name: String?,

    @Column(length = 100)
    val image: String?,

    @Column(length = 30, nullable = false)
    val type: String,

    @OneToMany(cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "friend_id")
    val members: List<FriendEntity>
) {
    fun toView(): ChatView = ChatView(id, name, image, type, members.map { it.toView() })
}