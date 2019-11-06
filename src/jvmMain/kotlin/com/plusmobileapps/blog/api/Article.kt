package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.API_VERSION
import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.Request
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.request.receive
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import java.util.*

const val ARTICLE_ENDPOINT = "$API_VERSION/article/"

fun Route.article(db: ArticleRepository) {
    post(ARTICLE_ENDPOINT) {
        val request = call.receive<Request>()
        val article = db.add(
            Article(
                author = request.author,
                dateCreated = request.dateCreated,
                title = request.title,
                minRead = request.minRead,
                body = request.body
            )
        )

        call.respond(article)
    }
}

