package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.User

interface ArticleRepository {
    suspend fun add(
        userId: String,
        authorValue: String,
        dateCreatedValue: String,
        titleValue: String,
        minReadValue: String,
        bodyValue: String
    )
    suspend fun getArticles(): List<Article>
    suspend fun getArticle(id: Int): Article?
    suspend fun deleteArticle(id: Int)
    suspend fun deleteAllArticles()
    suspend fun user(userId: String, hash: String? = null): User?
    suspend fun userByEmail(email: String): User?
    suspend fun userById(id: String): User?
    suspend fun createUser(user: User)

}