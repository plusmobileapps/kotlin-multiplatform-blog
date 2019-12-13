package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.redirect
import com.plusmobileapps.blog.repository.ArticleRepository
import com.plusmobileapps.blog.securityCode
import com.plusmobileapps.blog.verifyCode
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import java.util.*

const val ARTICLES = "/articles"

@Location(ARTICLES)
class Articles

fun Route.articles(db: ArticleRepository, hashFunction: (String) -> String) {
    get<Articles> {
        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }
        if (user == null) {
            call.redirect(SignIn())
        } else {
            val date = System.currentTimeMillis()
            val code = call.securityCode(date, user, hashFunction)
            call.respond(
                FreeMarkerContent(
                    template = "articles.ftl",
                    model = mapOf(
                        "articles" to db.getArticles(),
                        "displayName" to user.displayName,
                        "user" to user,
                        "date" to date,
                        "code" to code
                    ),
                    etag = user.userId
                )
            )
        }
    }
    post<Articles> {
        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }

        val params = call.receiveParameters()
        val date = params["date"]?.toLongOrNull() ?: return@post call.redirect(it)
        val code = params["code"] ?: return@post call.redirect(it)
        val action = params["action"] ?: throw IllegalArgumentException("Missing Parameter: action")

        if (user == null || !call.verifyCode(date, user, code, hashFunction)) {
            call.redirect(SignIn())
        }

        when (action) {
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
                    userId = user?.userId ?: "",
                    authorValue = author,
                    dateCreatedValue = Date(System.currentTimeMillis()).toString(),
                    titleValue = title,
                    minReadValue = minRead,
                    bodyValue = body
                )
            }
        }
        call.redirect(Articles())
    }

}