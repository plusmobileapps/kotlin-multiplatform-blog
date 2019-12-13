package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.redirect
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route
import io.ktor.sessions.clear
import io.ktor.sessions.sessions

const val SIGN_OUT = "/signout"

@Location(SIGN_OUT)
class SignOut

fun Route.signOut() {
    get<SignOut> {
        call.sessions.clear<BlogSession>()
        call.redirect(SignIn())
    }
}