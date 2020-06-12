package com.example.news.model.response

data class News(
    var status: String,
    var totalResults: Int,
    var articles: List<Article>
)
