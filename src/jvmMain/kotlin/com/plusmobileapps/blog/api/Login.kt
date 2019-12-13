package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.JwtService
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import hash
import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route

const val LOGIN_ENDPOINT = "/login"

@Location(LOGIN_ENDPOINT)
class Login

fun Route.login(db: ArticleRepository, jwtService: JwtService) {
    post<Login>() {
        val params = call.receive<Parameters>()
        val userId = params["userId"] ?: return@post call.redirect(it)
        val password = params["password"] ?: return@post call.redirect(it)
        val user = db.user(userId, hash(password))
        if (user != null) {
            val token = jwtService.generateToken(user)
            call.respond(token)
        } else {
            call.respondText("Invalid user")
        }
    }
}