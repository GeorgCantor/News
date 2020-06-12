package com.example.news.util

import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import android.widget.Toast.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return manager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

fun Context.shortToast(message: String) = makeText(this, message, LENGTH_SHORT).show()

fun Context.longToast(message: String) = makeText(this, message, LENGTH_LONG).show()

fun Context.loadImage(url: String?, view: ImageView) = Glide.with(this)
    .load(url)
    .placeholder(android.R.drawable.screen_background_dark_transparent)
    .thumbnail(0.1F)
    .into(view)

inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(owner, Observer { it?.apply(observer) })
}