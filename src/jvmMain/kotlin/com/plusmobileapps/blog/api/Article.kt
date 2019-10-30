package com.plusmobileapps.blog.api

import com.plusmobileapps.blog.API_VERSION
import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.Request
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post

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
    get(ARTICLES) {
        val articles = db.getArticles()
    }
}