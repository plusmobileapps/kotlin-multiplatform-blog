package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions

const val ABOUT = "/about"

@Location(ABOUT)
class About

fun Route.about(db: ArticleRepository) {
    get<About> {
        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }
        call.respond(FreeMarkerContent("about.ftl", mapOf("user" to user)))
    }
}