package com.x64tech.intune.entites

import com.x64tech.intune.models.views.FriendView
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "friends")
data class FriendEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID?,

    @Column(name = "status")
    val status: String,

    @OneToOne
    val initiator: UserEntity,

    @OneToOne
    val acceptor: UserEntity,
) {
    fun toView() : FriendView = FriendView(id!!, status, initiator.toView(), acceptor.toView())
}
