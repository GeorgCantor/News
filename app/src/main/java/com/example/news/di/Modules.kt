package com.example.news.di

import com.example.news.model.remote.ApiClient
import com.example.news.repository.Repository
import com.example.news.view.fragment.news.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiClient.create(get()) }
}

val repositoryModule = module {
    single { Repository(get()) }
}

val viewModelModule = module(override = true) {
    viewModel {
        NewsViewModel(androidApplication(), get())
    }
}