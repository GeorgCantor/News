package com.example.news.di

import com.example.news.view.fragment.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
//    single { ApiClient.create(get()) }
}

val repositoryModule = module {
//    single { Repository(get(), get()) }
}

val viewModelModule = module(override = true) {
    viewModel {
        NewsViewModel()
    }
}