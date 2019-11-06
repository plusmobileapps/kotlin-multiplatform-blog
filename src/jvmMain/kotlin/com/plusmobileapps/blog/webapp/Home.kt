package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

const val HOME = "/"

@Location(HOME)
class Home

fun Route.home(db: ArticleRepository) {
    get<Home> {
        call.respond(FreeMarkerContent("home.ftl", null))
    }
}