package com.plusmobileapps.blog

interface HomePageContract {

    interface View {
        fun showArticles(articles: List<Article>)
        fun showLoading(show: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun loadArticles()
    }

}