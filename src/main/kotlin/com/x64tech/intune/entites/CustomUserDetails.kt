package com.x64tech.intune.entites

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

data class CustomUserDetails(
    val id: UUID,
    val uname: String,
    val passwd: String,
    val eMail: String,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("ROLE_USER"))

    override fun getPassword(): String = passwd

    override fun getUsername(): String = uname
}
