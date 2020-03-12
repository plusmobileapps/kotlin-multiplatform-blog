package com.plusmobileapps.blog.model

import com.plusmobileapps.blog.Article
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

data class ServerArticle(
    override val id: Int,
    override val userId: String,
    override val author: String,
    override val dateCreated: String,
    override val title: String,
    override val minRead: String,
    override val body: String
) : Article,  Serializable


object Articles : IntIdTable() {
    val author: Column<String> = varchar("author", 200)
    val user: Column<String> = varchar("user_id", 20).index()
    val dateCreated: Column<String> = varchar("dateCreated", 50)
    val title: Column<String> = varchar("title", 200)
    val minRead: Column<String> = varchar("minRead", 100)
    val body: Column<String> = varchar("body", 50000)
}