package dev.sukhrob.inshorts.domain.repository

import androidx.lifecycle.LiveData
import dev.sukhrob.inshorts.domain.model.Article

interface InShortsRepository {

    // Load news
    fun getArticlesByCategory(category: String): List<Article>

    // Loading state
    fun loadingState(): LiveData<Boolean>

    // Update item
    fun updateArticle(item: Article)

    // Get Bookmarks
    fun getBookmarks(): LiveData<List<Article>>

}