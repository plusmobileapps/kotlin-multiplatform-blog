package com.plusmobileapps.blog.webapp

import MIN_PASSWORD_LENTH
import MIN_USER_ID_LENGTH
import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
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

const val SIGN_UP = "signup"

@Location(SIGN_UP)
data class SignUp(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val error: String = "",
    val passwordHash: String = ""
)

fun Route.signUp(db: ArticleRepository, hashFunction: (String) -> String) {

    post<SignUp> {
        val user = call.sessions.get<BlogSession>()?.let { session -> db.user(session.userId) }
        if (user != null) return@post call.redirect(Articles())

        val signUpParameters = call.receive<Parameters>()
        val userId = signUpParameters["userId"] ?: return@post call.redirect(it)
        val password = signUpParameters["password"] ?: return@post call.redirect(it)
        val displayName =   signUpParameters["displayName"] ?: return@post call.redirect(it)
        val email = signUpParameters["email"] ?: return@post call.redirect(it)

        val signUpError = SignUp(userId, displayName, email)

        when {
            password.length < MIN_PASSWORD_LENTH -> {
                call.redirect(signUpError.copy(error = "Password should be at least $MIN_PASSWORD_LENTH characters long"))
            }
            userId.length < MIN_USER_ID_LENGTH -> {
                call.redirect(signUpError.copy(error = "Username should be at least $MIN_USER_ID_LENGTH characters long"))
            }
            !userNameValid(userId) -> {
                call.redirect(signUpError.copy(error = "Username should consist of digits, letters, dots or underscores"))
            }
            db.user(userId) != null -> {
                call.redirect(signUpError.copy(error = "User with the following username is already registered"))
            }
            else -> {
                val hash = hashFunction(password)
                val newUser = User(userId, email, displayName, hash)
                try {
                    db.createUser(newUser)
                } catch (e: Throwable) {
                    when {
                        db.user(userId) != null -> {
                            call.redirect(signUpError.copy(error = "User with the following username is already registered"))
                        }
                        db.userByEmail(email) != null -> {
                            call.redirect(signUpError.copy(error = "User with the following email $email is already registered"))
                        }
                        else -> {
                            application.log.error("Failed to register user", e)
                            call.redirect(signUpError.copy(error = "failed to register"))
                        }
                    }
                }

                call.sessions.set(BlogSession(userId))
                call.redirect(Articles())
            }
        }
    }

    get<SignUp> {
        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }
        if (user != null) {
            call.redirect(Articles())
        } else {
            call.respond(FreeMarkerContent("signup.ftl", mapOf("error" to it.error)))
        }
    }

}