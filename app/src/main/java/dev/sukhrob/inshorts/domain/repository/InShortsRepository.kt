package dev.sukhrob.inshorts.domain.repository

import android.util.Log
import dev.sukhrob.inshorts.data.local.database.dao.ArticlesDao
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.api.InShortsApi
import dev.sukhrob.inshorts.data.remote.response.toEntity
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class InShortsRepository @Inject constructor(
    private val api: InShortsApi,
    private val dao: ArticlesDao,
) {

    suspend fun loadNews(category: String, internet: Boolean): Flow<List<Article>> = flow {
        if (internet) {
            try {
                val response = api.loadNewsByCategory(category)
                if (response.isSuccessful) {
                    response.body()?.let { baseDto ->
                        insertArticleList(baseDto.data.map { articleDto ->
                            articleDto.toEntity(baseDto.category)
                        })
                    }
                }
            } catch (e: Exception) {
                Log.d("TTT", e.message.toString())
            }
        }
        emit(getArticlesByCategory(category))
    }

    private suspend fun getArticlesByCategory(category: String) = dao.getArticlesByCategory(category)

    private suspend fun insertArticleList(articles: List<ArticleEntity>) =
        dao.insertAll(articles)

    suspend fun updateArticle(article: Article) = withContext(Dispatchers.IO) {
        dao.update(article.toEntity())
    }

    fun getBookmarks() = dao.getBookmarks()

}
