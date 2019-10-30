package com.plusmobileapps.blog.model

data class Request(
    val author: String,
    val dateCreated: String,
    val title: String,
    val minRead: String,
    val body: String
)