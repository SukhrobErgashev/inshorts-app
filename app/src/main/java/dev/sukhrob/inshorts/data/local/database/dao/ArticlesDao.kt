package dev.sukhrob.inshorts.data.local.database.dao

import androidx.room.*
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<ArticleEntity>)

    @Update
    suspend fun update(item: ArticleEntity)

    @Query("SELECT * FROM ArticleEntity  WHERE category = :category")
    suspend fun getArticlesByCategory(category: String): List<Article>

    @Query("SELECT * FROM ArticleEntity  WHERE isBookmark = 1")
    fun getBookmarks(): Flow<List<Article>>

    @Query("DELETE FROM ArticleEntity WHERE id = :id")
    suspend fun delete(id: Int)

}