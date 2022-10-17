package dev.sukhrob.inshorts.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao : BaseDao<ArticleEntity> {

    // get article list by article category
    @Query("SELECT * FROM ArticleEntity  WHERE category = :category")
    fun getArticlesByCategory(category: String): Flow<List<Article>>

    // get bookmark list
    @Query("SELECT * FROM ArticleEntity  WHERE isBookmark = 1")
    fun getBookmarks(): Flow<List<Article>>

    // get single article by id
//    @Query("SELECT * FROM ArticleEntity WHERE id = :id")
//    suspend fun getArticleById(id: Int)

    // used to delete article from 'articles' table
    @Query("DELETE FROM ArticleEntity WHERE id = :id")
    suspend fun delete(id: Int)

}