package com.plusmobileapps.blog.repository

import com.plusmobileapps.blog.model.ServerArticle
import com.plusmobileapps.blog.model.Articles
import com.plusmobileapps.blog.model.User
import com.plusmobileapps.blog.model.Users
import com.plusmobileapps.blog.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ArticlesRepository : ArticleRepository {

    override suspend fun add(
        userId: String,
        authorValue: String,
        dateCreatedValue: String,
        titleValue: String,
        minReadValue: String,
        bodyValue: String
    ): ServerArticle? = dbQuery {
        transaction {
            val insertStatement = Articles.insert {
                it[user] = userId
                it[author] = authorValue
                it[dateCreated] = dateCreatedValue
                it[title] = titleValue
                it[minRead] = minReadValue
                it[body] = bodyValue
            }
            val result = insertStatement.resultedValues?.get(0)
            if (result != null) {
                toArticle(result)
            } else {
                null
            }
        }
    }

    override suspend fun getArticles(): List<ServerArticle> = dbQuery {
        Articles.selectAll().map(this::toArticle)
    }

    override suspend fun getArticle(id: Int): ServerArticle? = dbQuery {
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

    private fun toArticle(row: ResultRow): ServerArticle {
        return ServerArticle(
            id = row[Articles.id].value,
            userId = row[Articles.user],
            author = row[Articles.author],
            dateCreated = row[Articles.dateCreated],
            title = row[Articles.title],
            minRead = row[Articles.minRead],
            body = row[Articles.body]
        )
    }

    override suspend fun user(userId: String, hash: String?): User? {
        val user = dbQuery {
            Users.select {
                (Users.id eq userId)
            }.mapNotNull(this::toUser)
                .singleOrNull()
        }
        return when {
            user == null -> null
            hash == null -> user
            user.passwordHash == hash -> user
            else -> null
        }
    }

    override suspend fun userByEmail(email: String): User? = dbQuery {
        Users.select { Users.email.eq(email) }
            .map(this::toUser)
            .singleOrNull()
    }

    override suspend fun userById(id: String): User? = dbQuery {
        Users.select { Users.id.eq(id) }
            .map(this::toUser)
            .singleOrNull()
    }

    override suspend fun createUser(user: User) = dbQuery {
        Users.insert {
            it[id] = user.userId
            it[displayName] = user.displayName
            it[email] = user.email
            it[passwordHash] = user.passwordHash
        }
        Unit
    }

    private fun toUser(row: ResultRow): User =
        User(
            userId = row[Users.id],
            displayName = row[Users.displayName],
            email = row[Users.email],
            passwordHash = row[Users.passwordHash]
        )

}