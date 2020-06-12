package com.example.news.model.response

data class Article(
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String?,
    var publishedAt: String,
    var source: Source,
    var content: String
)