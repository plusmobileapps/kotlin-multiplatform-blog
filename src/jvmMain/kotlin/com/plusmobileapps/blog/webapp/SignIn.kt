package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

const val SIGN_IN = "/signin"

@Location(SIGN_IN)
data class SignIn(val userId: String = "", val error: String = "")

fun Route.signIn(db: ArticleRepository, hashFunction: (String) -> String) {
    get<SignIn> {
        call.respond(FreeMarkerContent("signin.ftl", null))
    }
}