package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article

interface ArticleRepository {
    suspend fun add(authorValue: String, dateCreatedValue: String, titleValue: String, minReadValue: String, bodyValue: String)
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(id: Int): Article?
    suspend fun deleteArticle(id: Int)
    suspend fun deleteAllArticles()
}