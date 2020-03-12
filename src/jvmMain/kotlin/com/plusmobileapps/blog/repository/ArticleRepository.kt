package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.ServerArticle
import com.plusmobileapps.blog.model.User

interface ArticleRepository {
    suspend fun add(
        userId: String,
        authorValue: String,
        dateCreatedValue: String,
        titleValue: String,
        minReadValue: String,
        bodyValue: String
    ): ServerArticle?
    suspend fun getArticles(): List<ServerArticle>
    suspend fun getArticle(id: Int): ServerArticle?
    suspend fun deleteArticle(id: Int)
    suspend fun deleteAllArticles()
    suspend fun user(userId: String, hash: String? = null): User?
    suspend fun userByEmail(email: String): User?
    suspend fun userById(id: String): User?
    suspend fun createUser(user: User)

}