package dev.sukhrob.inshorts.data.remote.api

import dev.sukhrob.inshorts.data.remote.response.BaseDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InShortsApi {

    @GET("news")
    suspend fun getArticlesByCategory(@Query("category") category: String): Response<BaseDto>

}