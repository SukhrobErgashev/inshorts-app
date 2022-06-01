package dev.sukhrob.inshorts.data.remote.response

data class BaseDto(
    val category: String,
    val data: List<ArticleDto>
)

//data class BaseDto(
//    val category: String,
//    //val total: Int,
//    val articles: List<ArticleDto>
//)