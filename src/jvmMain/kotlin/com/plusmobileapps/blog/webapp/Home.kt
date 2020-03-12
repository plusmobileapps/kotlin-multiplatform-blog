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
    val intArray = intArrayOf()
    intArray.forEachIndexed { index, i ->

    }
    get<Home> {
        //        val user = call.sessions.get<BlogSession>()?.let { db.user(it.userId) }
//        call.respond(FreeMarkerContent("home.ftl", mapOf("user" to user)))
        call.respondHtml {
            head {
                title("Plus Mobile Apps")
                unsafe {
                    +"""
                            <meta name="viewport" content="minimum-scale=1, initial-scale=1, width=device-width, shrink-to-fit=no">

                          <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <style data-jss="" data-meta="MuiCssBaseline">
    html {
      box-sizing: border-box;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
    }
    *, *::before, *::after {
      box-sizing: inherit;
    }
    body {
      margin: 0;
      background-color: #fafafa;
    }
    @media print {
      body {
        background-color: #fff;
      }
    }
    </style>
                    """.trimIndent()
                }
            }
            body {
//                +"${hello()} from Ktor. Check me value: ${Sample().checkMe()}"
//                div {
//                    id = "js-response"
//                    +"Loading..."
//                }

                div {
                    id = "root"
                }

                script(src = "/static/kotlin-multiplatform-blog.js") {}


            }
        }
    }
}