package com.plusmobileapps.blog

import com.plusmobileapps.blog.api.article
import com.plusmobileapps.blog.model.BlogSession
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.repository.ArticlesRepository
import com.plusmobileapps.blog.repository.DatabaseFactory
import com.plusmobileapps.blog.webapp.*
import freemarker.cache.ClassTemplateLoader
import hash
import hashKey
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.locations.Locations
import io.ktor.locations.locations
import io.ktor.request.header
import io.ktor.request.host
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import java.net.URI
import java.util.concurrent.TimeUnit

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

        install(Locations)

        install(Sessions) {
            cookie<BlogSession>("SESSION") {
                transform(SessionTransportTransformerMessageAuthentication(hashKey))
            }
        }

        val hashFunction: (String) -> String = { s: String -> hash(s) }

        DatabaseFactory.init()

        install(ContentNegotiation) {
            gson {

            }
        }

        routing {
            val repository = ArticlesRepository()

            static("/static") {
                resources("images")
            }
            home(repository)
            about(repository)

            article(repository)
            articles(repository, hashFunction)
            signIn(repository, hashFunction)
            signUp(repository, hashFunction)
            signOut()
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

suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}

fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
    securityCode(date, user, hashFunction) == code
            && (System.currentTimeMillis() - date).let { it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS) }

fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }