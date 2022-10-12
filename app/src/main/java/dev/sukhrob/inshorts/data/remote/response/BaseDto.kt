package dev.sukhrob.inshorts.data.remote.response

data class BaseDto(
    val category: String,
    val data: List<ArticleDto>
)