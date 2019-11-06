package com.plusmobileapps.blog

import com.plusmobileapps.blog.api.article
import com.plusmobileapps.blog.api.articles
import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.repository.InMemoryRepository
import com.plusmobileapps.blog.webapp.about
import com.plusmobileapps.blog.webapp.home
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.basic
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.launch

actual class Sample {
    actual fun checkMe() = 42
}

actual object Platform {
    actual val name: String = "JVM"
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {

        install(DefaultHeaders)

        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            }
        }

        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }

        install(Authentication) {
            basic(name = "auth") {
                realm = "Ktor server"
                validate { credentials ->
                    if (credentials.password == "${credentials.name}123") {
                        User(credentials.name)
                    } else {
                        null
                    }
                }
            }
        }

        install(ContentNegotiation) {
            gson {

            }
        }

        routing {
            val repository = InMemoryRepository()

            static("/static") {
                resources("images")
            }
            home(repository)
            about()

            article(repository)
            articles(repository)
//            get("/") {
//                call.respondHtml {
//                    head {
//                        title("Hello from Ktor!")
//                    }
//                    body {
//                        +"${hello()} from Ktor. Check me value: ${Sample().checkMe()}"
//                        div {
//                            id = "js-response"
//                            +"Loading..."
//                        }
//                        script(src = "/static/kotlin-multiplatform-blog.js") {}
//                    }
//                }
//            }

        }
    }.start(wait = true)
}

const val API_VERSION = "/api/v1"