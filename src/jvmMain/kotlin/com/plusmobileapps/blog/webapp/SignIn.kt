package com.plusmobileapps.blog.webapp

import MIN_PASSWORD_LENTH
import MIN_USER_ID_LENGTH
import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.Parameters
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import userNameValid

const val SIGN_IN = "/signin"

@Location(SIGN_IN)
data class SignIn(val userId: String = "", val error: String = "")

fun Route.signIn(db: ArticleRepository, hashFunction: (String) -> String) {

    post<SignIn> {
        val signInParameters = call.receive<Parameters>()
        val userId = signInParameters["userId"] ?: return@post call.redirect(it)
        val password = signInParameters["password"] ?: return@post call.redirect(it)

        val signInError = SignIn(userId)

        val signIn = when {
            userId.length < MIN_USER_ID_LENGTH -> null
            password.length < MIN_PASSWORD_LENTH -> null
            !userNameValid(userId) -> null
            else -> db.user(userId)
        }

        if (signIn == null) {
            call.redirect(signInError.copy(error = "Invalid username or password"))
        } else {
            call.sessions.set(BlogSession(signIn.userId))
            call.redirect(Articles())
        }
    }

    get<SignIn> {
        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }

        if (user != null) {
            call.redirect(Home())
        } else {
            call.respond(FreeMarkerContent("signin.ftl", mapOf("userId" to it.userId, "error" to it.error)))
        }
    }
}