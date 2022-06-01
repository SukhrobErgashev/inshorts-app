package dev.sukhrob.inshorts.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sukhrob.inshorts.data.local.dao.InShortsDao
import dev.sukhrob.inshorts.data.local.entity.ArticleEntity
import dev.sukhrob.inshorts.data.remote.api.InShortsApi
import dev.sukhrob.inshorts.data.remote.response.BaseDto
import dev.sukhrob.inshorts.data.remote.response.toArticleEntity
import dev.sukhrob.inshorts.domain.model.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class InShortsRepositoryImpl @Inject constructor(
    private val api: InShortsApi,
    private val dao: InShortsDao,
) : InShortsRepository {

    private val loadingState = MutableLiveData(false)

    override fun getArticlesByCategory(category: String): List<Article> {
        loadingState.value = true
        api.getArticlesByCategory(category).enqueue(object : Callback<BaseDto> {
            override fun onResponse(call: Call<BaseDto>, response: Response<BaseDto>) {
                loadingState.value = false
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()?.data ?: arrayListOf()
                    dao.insertAll(result.map { it.toArticleEntity(category) })
                }
            }

            override fun onFailure(call: Call<BaseDto>, t: Throwable) {
                loadingState.value = false
            }

        })

        return dao.getArticlesByCategory(category)
    }

    override fun loadingState(): LiveData<Boolean> = loadingState

    override fun updateArticle(item: Article) {
        dao.update(item as ArticleEntity)
    }

    override fun getBookmarks(): LiveData<List<Article>> {
        return dao.getBookmarks()
    }
}