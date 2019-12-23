package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.API_VERSION
import com.plusmobileapps.blog.api.requests.ArticlesApiRequest
import com.plusmobileapps.blog.apiUser
import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route

const val ARTICLES_API_ENDPOINT = "$API_VERSION/articles/"

@Location(ARTICLES_API_ENDPOINT)
class ArticlesApi

fun Route.articlesApi(db: ArticleRepository) {

//    authenticate("jwt") {
        get<ArticlesApi>() {
            call.respond(db.getArticles())
        }

        post<ArticlesApi> {
            val user = call.apiUser ?: return@post call.redirect(it)

            try {
                val request = call.receive<ArticlesApiRequest>()
                val article: Article? = db.add(
                    userId = user.userId,
                    authorValue = request.author,
                    dateCreatedValue = request.dateCreated,
                    bodyValue = request.body,
                    minReadValue = request.minRead,
                    titleValue = request.title
                )
                if (article != null) {
                    call.respond(article)
                } else {
                    call.respondText("Invalid data submitted", status = HttpStatusCode.InternalServerError)
                }
            } catch (e: Throwable) {
                call.respondText("Invalid data received: $e", status = HttpStatusCode.BadRequest)
            }
//        }
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

