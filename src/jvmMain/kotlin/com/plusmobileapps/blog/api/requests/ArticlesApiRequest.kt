package com.plusmobileapps.blog.api.requests

data class ArticlesApiRequest(
    val author: String,
    val dateCreated: String,
    val title: String,
    val minRead: String,
    val body: String
)