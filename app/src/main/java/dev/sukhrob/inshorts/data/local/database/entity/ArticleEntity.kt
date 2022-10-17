package dev.sukhrob.inshorts.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.sukhrob.inshorts.domain.model.Article

@Entity
data class ArticleEntity(
    @PrimaryKey
    val id: String,
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
)

fun ArticleEntity.toModel(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        date = date,
        imageUrl = imageUrl,
        readMoreUrl = readMoreUrl,
        time = time,
        title = title,
        url = url,
        category = category,
        isBookmark = isBookmark
    )
}