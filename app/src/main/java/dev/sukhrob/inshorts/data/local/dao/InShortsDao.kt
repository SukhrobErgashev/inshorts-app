package dev.sukhrob.inshorts.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface InShortsDao : BaseDao<ArticleEntity> {

    @Query("SELECT * FROM ArticleEntity  WHERE category = :category")
    suspend fun getArticlesByCategory(category: String): List<Article>

    @Query("SELECT * FROM ArticleEntity  WHERE isBookmark = 1")
    fun getBookmarks(): Flow<List<Article>>

}