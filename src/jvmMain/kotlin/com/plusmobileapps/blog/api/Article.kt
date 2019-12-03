package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.API_VERSION
import com.plusmobileapps.blog.model.Request
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

const val ARTICLE_ENDPOINT = "$API_VERSION/article/"

fun Route.article(db: ArticleRepository) {
    post(ARTICLE_ENDPOINT) {
        val request = call.receive<Request>()
        val article = db.add(
            userId = "",
            authorValue = request.author,
            dateCreatedValue = request.dateCreated,
            titleValue = request.title,
            minReadValue = request.minRead,
            bodyValue = request.body
        )

        call.respond(article)
    }
}

