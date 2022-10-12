package dev.sukhrob.inshorts.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sukhrob.inshorts.data.local.dao.InShortsDao
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.api.InShortsApi
import dev.sukhrob.inshorts.data.remote.response.BaseDto
import dev.sukhrob.inshorts.data.remote.response.toEntity
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.Resource
import dev.sukhrob.inshorts.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class InShortsRepositoryImpl @Inject constructor(
    private val api: InShortsApi,
    private val dao: InShortsDao,
) : InShortsRepository {

    override fun getArticlesByCategory(category: String) = flow<Resource<List<Article>>> {

        val response = api.getArticlesByCategory(category)

        if (response.isSuccessful) {
            response.body()?.let { baseDto ->
                dao.insertAll(baseDto.data.map { it.toEntity(baseDto.category) })
            }
            emit(Resource.Success(dao.getArticlesByCategory(category)))
        } else {
            emit(Resource.Error("Oops, something is wrong!"))
        }
        dao.getArticlesByCategory(category)

    }.catch {
        emit(Resource.Error("Oops, something is wrong!"))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateArticle(item: ArticleEntity) {
        dao.update(item)
    }

    override fun getBookmarks() = dao.getBookmarks()
}