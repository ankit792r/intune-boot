package com.x64tech.intune.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    private fun getSigningKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode("uidfboadfnsodfbsolfblwfiowebmsnsldfgldfgdfbgoefbgkjgdfbglkdfjgbdlkfjbufuolfqebofbaugfoukejbaf")
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(subject:String): String {
        val claims: MutableMap<String, Any> = mutableMapOf()

        return Jwts.builder()
            .subject(subject)
            .claims(claims)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 60 * 1000 * 60))
            .signWith(getSigningKey())
            .compact()
    }

    fun getUsernameFromToken(token: String): String? {
        return extractClaims(token).subject
    }

    private fun extractClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}