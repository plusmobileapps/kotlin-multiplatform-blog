package com.plusmobileapps.blog

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

class HomePage (private val presenter: HomePageContract.Presenter) : HomePageContract.View {

    private val loader = document.getElementById("loader") as HTMLDivElement
    private val content = document.getElementById("content") as HTMLDivElement

    init {
//        val loading = document.
    }

    override fun showArticles(articles: List<Article>) {
    }

    override fun showLoading(show: Boolean) {
        loader.style.visibility = if (show) {
            "visible"
        } else {
            "hidden"
        }
    }

}