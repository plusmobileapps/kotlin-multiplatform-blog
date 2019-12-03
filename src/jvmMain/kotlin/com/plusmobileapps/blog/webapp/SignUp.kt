package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

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
    get<SignUp> {
        call.respond(FreeMarkerContent("signup.ftl", null))
    }
}