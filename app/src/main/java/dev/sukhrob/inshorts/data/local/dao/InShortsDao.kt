package dev.sukhrob.inshorts.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.domain.model.Article

@Dao
interface InShortsDao : BaseDao<ArticleEntity> {

    @Query("SELECT * FROM ArticleEntity  WHERE category = :category")
    fun getArticlesByCategory(category: String): List<Article>

    @Query("SELECT * FROM ArticleEntity  WHERE isBookmark = 1")
    fun getBookmarks(): LiveData<List<Article>>

}