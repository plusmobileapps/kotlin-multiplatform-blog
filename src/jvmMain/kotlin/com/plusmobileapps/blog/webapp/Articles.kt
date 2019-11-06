package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import java.util.*

const val ARTICLES = "/articles"

@Location(ARTICLES)
class Articles

fun Route.articles(db: ArticleRepository) {
    authenticate("auth") {
        get<Articles> {
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
        post<Articles> {
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
            call.redirect(Articles())
        }
    }
}