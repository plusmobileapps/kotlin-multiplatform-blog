package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

const val HOME = "/"

fun Route.home(db: ArticleRepository) {
    get(HOME) {
        call.respond(FreeMarkerContent("homepage.ftl", mapOf("articles" to db.getArticles())))
//        call.respondText("Hello from home page")
    }
}