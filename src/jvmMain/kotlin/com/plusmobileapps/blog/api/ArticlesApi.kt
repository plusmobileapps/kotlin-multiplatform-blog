package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.API_VERSION
import com.plusmobileapps.blog.model.Request
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

const val ARTICLES_API_ENDPOINT = "$API_VERSION/articles/"

@Location(ARTICLES_API_ENDPOINT)
class ArticlesApi

fun Route.articlesApi(db: ArticleRepository) {

    authenticate("jwt") {
        get<ArticlesApi>() {
            call.respond(db.getArticles())
        }
    }



//    post(ARTICLES_API_ENDPOINT) {
//        val request = call.receive<Request>()
//        val article = db.add(
//            userId = "",
//            authorValue = request.author,
//            dateCreatedValue = request.dateCreated,
//            titleValue = request.title,
//            minReadValue = request.minRead,
//            bodyValue = request.body
//        )
//
//        call.respond(article)
//    }
}

