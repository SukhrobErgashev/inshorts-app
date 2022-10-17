package dev.sukhrob.inshorts.domain.model;

import android.os.Parcelable
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
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
    var isBookmark: Boolean
) : Parcelable

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
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