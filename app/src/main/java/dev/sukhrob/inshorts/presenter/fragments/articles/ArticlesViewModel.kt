package dev.sukhrob.inshorts.presenter.fragments.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sukhrob.inshorts.domain.model.Article


interface ArticlesViewModel {
    val loading: LiveData<Boolean>
    val articles: LiveData<List<Article>>
    val error: LiveData<String>

    fun loadArticlesByCategory(category: String)
    fun update(item: Article)
}