package dev.sukhrob.inshorts.domain.repository

import android.util.Log
import dev.sukhrob.inshorts.data.local.database.dao.ArticlesDao
import dev.sukhrob.inshorts.data.local.database.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.api.InShortsApi
import dev.sukhrob.inshorts.data.remote.response.ArticleDto
import dev.sukhrob.inshorts.data.remote.response.toEntity
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.Resource
import dev.sukhrob.inshorts.domain.model.toEntity
import dev.sukhrob.inshorts.utils.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class InShortsRepositoryImpl @Inject constructor(
    private val api: InShortsApi,
    private val dao: ArticlesDao,
) : InShortsRepository {

    override suspend fun loadNews(category: String, internet: Boolean) = withContext(Dispatchers.IO){
        if (internet) {
            try {
                val response = api.loadNewsByCategory(category)
                if (response.isSuccessful) {
                    response.body()?.let { baseDto ->
                        Log.d("PPP", "loadNews: ${baseDto.category}")
                        insertArticleList(baseDto.data.map { articleDto ->
                            articleDto.toEntity(baseDto.category)
                        })
                    }
                }
            } catch (e: Exception) {
                Log.d("TTT", "Unknown Error")
            }
        }
        getArticlesByCategory(category)
    }

    override suspend fun insertArticleList(articles: List<ArticleEntity>) =
        dao.insertAll(articles)

    override suspend fun updateArticle(article: Article) = withContext(Dispatchers.IO) {
        dao.update(article.toEntity())
    }

    override fun getArticlesByCategory(category: String) = dao.getArticlesByCategory(category)

    override fun getBookmarks() = dao.getBookmarks()

}
