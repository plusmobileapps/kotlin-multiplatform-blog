package com.plusmobileapps.blog.webapp

import com.plusmobileapps.blog.Sample
import com.plusmobileapps.blog.hello
import com.plusmobileapps.blog.repository.ArticleRepository
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route
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
                title("Plus Mobile Apps")
            }
            body {
                +"${hello()} from Ktor. Check me value: ${Sample().checkMe()}"
                div {
                    id = "js-response"
                    +"Loading..."
                }


                div("loader") {
                    id = "loader"
                    svg("spinner") {

                    }
                }

                script(src = "/static/kotlin-multiplatform-blog.js") {}
            }
        }
    }
}