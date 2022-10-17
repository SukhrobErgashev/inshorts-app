package dev.sukhrob.inshorts.domain.repository

import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.response.ArticleDto
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface InShortsRepository {

    // load news
    suspend fun loadNews(category: String, internet: Boolean): Flow<List<Article>>

    // insert article list
    suspend fun insertArticleList(articles: List<ArticleEntity>)

    // update article
    suspend fun updateArticle(article: Article)

    // get articles by category
    fun getArticlesByCategory(category: String): Flow<List<Article>>

    // Get Bookmarks
    fun getBookmarks(): Flow<List<Article>>

}