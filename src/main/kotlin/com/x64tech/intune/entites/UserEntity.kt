package com.x64tech.intune.entites

import com.x64tech.intune.models.views.UserView
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true, length = 60)
    val email: String,

    @Column(nullable = false, length = 240)
    val password: String,

    @Column(nullable = false, unique = true, length = 100)
    val username: String,

    @Column(length = 120)
    val name: String?,

    @Column(length = 200)
    val bio: String?,
) {
    fun toView(): UserView = UserView(id!!, username, name, email, bio)

    fun toUserDetails(): CustomUserDetails = CustomUserDetails(id!!, username, password, email)
}
