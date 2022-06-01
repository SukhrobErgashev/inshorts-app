package dev.sukhrob.inshorts.presenter.fragments.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sukhrob.inshorts.domain.model.Article


interface ArticlesViewModel {
    val loadingLiveData: LiveData<Boolean>
    val articlesLiveData: MutableLiveData<List<Article>>

    fun loadArticlesByCategory(category: String)
    fun update(item: Article)
}