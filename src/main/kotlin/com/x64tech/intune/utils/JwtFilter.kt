package com.x64tech.intune.utils

import com.x64tech.intune.services.DetailService
import com.x64tech.intune.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val detailService: DetailService,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val token = header.substringAfter("Bearer ")
            val username = jwtService.getUsernameFromToken(token)
            val customUserDetails = detailService.loadUserByUsername(username)
            val authenticationToken =
                UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    customUserDetails.password,
                    customUserDetails.authorities
                )
            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            logger.error("Error while doing filter", e)
        }

        filterChain.doFilter(request, response)
    }
}