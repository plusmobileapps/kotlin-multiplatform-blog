package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article

interface ArticleRepository {
    suspend fun add(article: Article)
    suspend fun addArticles(articles: List<Article>)
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(id: Int): Article
    suspend fun deleteArticle(id: Int)
    suspend fun deleteAllArticles()
}