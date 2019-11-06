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
const val ARTICLES = "/articles"

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

fun Route.articles(db: ArticleRepository) {
    authenticate("auth") {
        get(ARTICLES) {
            val user = call.authentication.principal as User
            call.respond(
                FreeMarkerContent(
                    template = "articles.ftl",
                    model = mapOf(
                        "articles" to db.getArticles(),
                        "displayName" to user.displayName
                    )
                )
            )
        }
        post(ARTICLES) {
            val params = call.receiveParameters()
            val action = params["action"] ?: throw IllegalArgumentException("Missing Parameter: action")

            when(action) {
                "delete" -> {
                    val id = params["id"] ?: throw IllegalArgumentException("Missing Parameter: id")
                    db.deleteArticle(id.toInt())
                }
                "add" -> {
                    val author = params["author"] ?: throw IllegalArgumentException("Missing parameter: author")
                    val title = params["title"] ?: throw IllegalArgumentException("Missing paramter: title")
                    val minRead = params["minRead"] ?: throw IllegalArgumentException("Missing Parameter: minRead")
                    val body = params["body"] ?: throw IllegalArgumentException("missing parameter: body")
                    db.add(
                        Article(
                            author = author,
                            dateCreated = Date(System.currentTimeMillis()).toString(),
                            title = title,
                            minRead = minRead,
                            body = body
                        )
                    )
                }
            }
            call.respondRedirect(ARTICLES)
        }
    }
}