package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.redirect
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route

const val SIGN_OUT = "/signout"

@Location(SIGN_OUT)
class SignOut

fun Route.signOut() {
    get<SignOut> {
        call.redirect(SignIn())
    }
}