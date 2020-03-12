package com.plusmobileapps.blog

import react.dom.render
import kotlin.browser.*

actual class Sample {
    actual fun checkMe() = 12
}

actual object Platform {
    actual val name: String = "JS"
}


@Suppress("unused")
@JsName("helloWorld")
fun helloWorld(salutation: String) {
    val message = "$salutation from Kotlin.JS ${hello()}, check me value: ${Sample().checkMe()}"
    document.getElementById("js-response")?.textContent = message
}

fun main() {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
//    document.addEventListener("DOMContentLoaded", {2
//        helloWorld("Hi andrew!")
//        val homePagePresenter = HomePagePresenter()
//        val homePage = HomePage(presenter = homePagePresenter)
//        homePagePresenter.attach(homePage)
//        homePagePresenter.loadArticles()
//    })
}

