package com.plusmobileapps.blog

interface Article {
    val id: Int
    val userId: String
    val author: String
    val dateCreated: String
    val title: String
    val minRead: String
    val body: String
}