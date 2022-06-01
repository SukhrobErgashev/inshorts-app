package dev.sukhrob.inshorts.data.remote.response


import com.google.gson.annotations.SerializedName
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity

data class ArticleDto(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("readMoreUrl")
    val readMoreUrl: String?,
    @SerializedName("time")
    val time: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)

fun ArticleDto.toArticleEntity(category: String) = ArticleEntity(
    author,
    content,
    date,
    imageUrl,
    readMoreUrl,
    time,
    title,
    url,
    category
)