package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

const val HOME = "/"

fun Route.home(db: ArticleRepository) {
    get(HOME) {
        call.respondText {
            db.getArticles().toString()
        }
    }
}