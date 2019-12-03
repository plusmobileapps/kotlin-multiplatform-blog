package com.plusmobileapps.blog.model

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

data class Article(
    val id: Int,
    val userId: String,
    val author: String,
    val dateCreated: String,
    val title: String,
    val minRead: String,
    val body: String
) : Serializable


object Articles : IntIdTable() {
    val author: Column<String> = varchar("author", 200)
    val userId: Column<String> = varchar("user_id", 20).index()
    val dateCreated: Column<String> = varchar("dateCreated", 50)
    val title: Column<String> = varchar("title", 200)
    val minRead: Column<String> = varchar("minRead", 100)
    val body: Column<String> = varchar("body", 50000)
}