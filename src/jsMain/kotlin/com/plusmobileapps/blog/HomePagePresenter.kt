package com.plusmobileapps.blog

import org.w3c.xhr.XMLHttpRequest

class HomePagePresenter : HomePageContract.Presenter {

    private lateinit var view: HomePageContract.View

    override fun attach(view: HomePageContract.View) {
        this.view = view
    }

    override fun loadArticles() {
        view.showLoading(true)
        getAsync("http://0.0.0.0:8081/api/v1/articles") { response ->
            val articles = JSON.parse<Array<Article>>(response)
            view.showLoading(false)
            view.showArticles(articles.toList())
        }
    }

    private fun getAsync(url: String, callback: (String) -> Unit) {
        // 2
        val xmlHttp = XMLHttpRequest()
        // 3
        xmlHttp.open("GET", url)
        // 4
        xmlHttp.onload = {
            // 5
            if (xmlHttp.readyState == 4.toShort() && xmlHttp.status == 200.toShort()) {
                // 6
                callback.invoke(xmlHttp.responseText)
            }
        }
        // 7
        xmlHttp.send()
    }
}