package dev.sukhrob.inshorts.data.remote.response


import com.google.gson.annotations.SerializedName
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity

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

fun ArticleDto.toEntity(category: String) = ArticleEntity(
    author = author,
    content = content,
    date = date,
    imageUrl = imageUrl,
    readMoreUrl = readMoreUrl,
    time = time,
    title = title,
    url = url,
    category = category
)