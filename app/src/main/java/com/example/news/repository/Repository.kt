package com.example.news.repository

import com.example.news.model.remote.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getNews(query: String, page: Int) = apiService.getNews(query, page)
}