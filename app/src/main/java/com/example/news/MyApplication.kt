package com.example.news

import android.app.Application
import com.example.news.di.apiModule
import com.example.news.di.repositoryModule
import com.example.news.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(apiModule, repositoryModule, viewModelModule))
        }
    }
}