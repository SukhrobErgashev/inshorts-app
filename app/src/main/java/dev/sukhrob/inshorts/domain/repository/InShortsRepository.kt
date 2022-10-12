package dev.sukhrob.inshorts.domain.repository

import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface InShortsRepository {

    // Load news
    fun getArticlesByCategory(category: String): Flow<Resource<List<Article>>>

    // Update item
    suspend fun updateArticle(item: ArticleEntity)

    // Get Bookmarks
    fun getBookmarks(): Flow<List<Article>>

}