package dev.sukhrob.inshorts.utils.viewState

import dev.sukhrob.inshorts.domain.model.Article

sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val articles: List<Article>) : ViewState()
    data class Error(val errorMessage: String) : ViewState()
}