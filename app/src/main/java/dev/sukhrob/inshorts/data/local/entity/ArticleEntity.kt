package dev.sukhrob.inshorts.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    val author: String,
    val content: String,
    val date: String,
    val imageUrl: String,
    val readMoreUrl: String?,
    val time: String,
    @PrimaryKey
    val title: String,
    val url: String,
    val category: String,
    var isBookmark: Boolean = false
)