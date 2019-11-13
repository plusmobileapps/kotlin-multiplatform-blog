package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.Article
import com.plusmobileapps.blog.model.Articles
import com.plusmobileapps.blog.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ArticlesRepository : ArticleRepository {

    override suspend fun add(authorValue: String, dateCreatedValue: String, titleValue: String, minReadValue: String, bodyValue: String) {
        transaction {
            Articles.insert {
                it[author] = authorValue
                it[dateCreated] = dateCreatedValue
                it[title] = titleValue
                it[minRead] = minReadValue
                it[body] = bodyValue
            }
        }
    }

    override suspend fun getArticles(): List<Article> = dbQuery {
        Articles.selectAll().map(this::toArticle)
    }

    override suspend fun getArticle(id: Int): Article? = dbQuery {
        Articles.select {
            (Articles.id eq id)
        }.mapNotNull(::toArticle)
            .singleOrNull()
    }

    override suspend fun deleteArticle(id: Int) {
        requireNotNull(getArticle(id)) { "No article found for id $id" }
        return dbQuery {
            Articles.deleteWhere { Articles.id eq id }
        }
    }

    override suspend fun deleteAllArticles() {
        Articles.deleteAll()
    }

    private fun toArticle(row: ResultRow): Article {
        return Article(
            id = row[Articles.id].value,
            author = row[Articles.author],
            dateCreated = row[Articles.dateCreated],
            title = row[Articles.title],
            minRead = row[Articles.minRead],
            body = row[Articles.body]
        )
    }

}