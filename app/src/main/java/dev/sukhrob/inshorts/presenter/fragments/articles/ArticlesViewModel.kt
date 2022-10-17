package dev.sukhrob.inshorts.presenter.fragments.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.utils.viewState.ViewState
import kotlinx.coroutines.flow.StateFlow


interface ArticlesViewModel {
    val uiState: StateFlow<ViewState>

    fun loadArticlesByCategory(category: String)
    fun update(item: Article)
}