package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger

class InMemoryRepository : ArticleRepository {

    private val idCounter = AtomicInteger()
    private val articles = hashMapOf<Int, Article>()

    override suspend fun add(article: Article): Article {
        return article.id?.let { id ->
            articles[id]
        } ?: with(article) {
            val id = idCounter.incrementAndGet()
            article.id = id
            articles[id] = article
            article
        }
    }

    override suspend fun addArticles(articles: List<Article>) = articles.forEach { article -> add(article) }

    override suspend fun getArticles(): List<Article> = articles.values.toList()

    override suspend fun getArticle(id: Int): Article {
        return articles[id] ?: throw IllegalArgumentException("No article found for $id")
    }

    override suspend fun deleteArticle(id: Int) {
        articles.remove(id)
    }

    override suspend fun deleteAllArticles() = articles.clear()

}