package com.example.news.view.fragment.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.repository.Repository
import com.example.news.util.Constants.ERROR_504
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class NewsViewModel(
    app: Application,
    private val repository: Repository
) : AndroidViewModel(app) {

    val news = MutableLiveData<List<Article>>()
    val nestedNews = MutableLiveData<List<Article>>()
    val isProgressVisible = MutableLiveData<Boolean>().apply { this.value = true }
    val error = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable.message) {
            ERROR_504 -> error.postValue(app.baseContext.getString(R.string.internet_unavailable))
            else -> error.postValue(throwable.message)
        }
        isProgressVisible.postValue(false)
    }

    fun getNews(query: String, page: Int) {
        viewModelScope.launch(exceptionHandler) {
            repository.getNews(query, page).apply {
                errorBody()?.let { error.postValue(it.toString()) }
                news.postValue(body()?.articles)
            }
            repository.getNews(getTopic(page), 1).apply {
                errorBody()?.let { error.postValue(it.toString()) }
                nestedNews.postValue(body()?.articles)
            }
            isProgressVisible.postValue(false)
        }
    }

    private fun getTopic(index: Int) = when (index) {
        1 -> "Sport"
        2 -> "World"
        3 -> "Europe"
        4 -> "Cinema"
        5 -> "Science"
        else -> "Russia"
    }
}