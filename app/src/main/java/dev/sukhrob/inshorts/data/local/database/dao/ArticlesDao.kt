package dev.sukhrob.inshorts.data.local.database.dao

import androidx.room.*
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.response.ArticleDto
import dev.sukhrob.inshorts.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ArticleEntity>)

    @Update
    suspend fun update(item: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity  WHERE category = :category")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM ArticleEntity  WHERE isBookmark = 1")
    fun getBookmarks(): Flow<List<Article>>

}