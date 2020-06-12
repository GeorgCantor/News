package com.example.news.view.fragment.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.news.MyApplication
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

    private val context = getApplication<MyApplication>()

    val news = MutableLiveData<List<Article>>()
    val isProgressVisible = MutableLiveData<Boolean>().apply { this.value = true }
    val error = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable.message) {
            ERROR_504 -> error.postValue(context.getString(R.string.internet_unavailable))
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
        }
    }
}