package com.example.news.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String?,
    var publishedAt: String,
    var source: Source,
    var content: String
) : Parcelable