package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.Sample
import com.plusmobileapps.blog.hello
import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.respondHtml
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.html.*

const val HOME = "/"

@Location(HOME)
class Home

fun Route.home(db: ArticleRepository) {
    get<Home> {
//        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }
//        call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
        call.respondHtml {
            head {
                title("Hello from Ktor!")
            }
            body {
                +"${hello()} from Ktor. Check me value: ${Sample().checkMe()}"
                div {
                    id = "js-response"
                    +"Loading..."
                }
                script(src = "/static/kotlin-multiplatform-blog.js") {}
            }
        }
    }
}