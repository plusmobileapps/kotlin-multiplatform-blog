package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article

class InMemoryRepository : ArticleRepository {

    private val articles = hashMapOf<Int, Article>()

    override suspend fun add(article: Article) {
        articles[article.id!!] = article
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addArticles(articles: List<Article>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getArticles(): List<Article> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getArticle(id: Int): Article {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteArticle(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllArticles() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}