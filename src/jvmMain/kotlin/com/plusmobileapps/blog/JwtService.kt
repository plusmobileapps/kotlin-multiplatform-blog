package com.plusmobileapps.blog

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.plusmobileapps.blog.model.User
import java.util.*

class JwtService {

    private val issuer = "plusmobileappsblog"
    private val JWT_SECRET = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(JWT_SECRET)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", user.userId)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() = Date(System.currentTimeMillis() + 3_600_000 + 24) //24hours



}