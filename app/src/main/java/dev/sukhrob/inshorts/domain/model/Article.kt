package dev.sukhrob.inshorts.domain.model;

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val author: String,
    val content: String,
    val date: String,
    val imageUrl: String,
    val readMoreUrl: String?,
    val time: String,
    val title: String,
    val url: String,
    val category: String,
    var isBookmark: Boolean = false
) : Parcelable