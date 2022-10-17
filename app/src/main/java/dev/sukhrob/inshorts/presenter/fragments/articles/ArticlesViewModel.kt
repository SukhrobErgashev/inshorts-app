package dev.sukhrob.inshorts.presenter.fragments.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import dev.sukhrob.inshorts.utils.viewState.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: InShortsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Empty)
    val uiState: StateFlow<ViewState> get() = _uiState

    fun loadArticlesByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            repository.loadNews(category, true).collect { result ->
                if (result.isEmpty()) {
                    _uiState.value = ViewState.Empty
                } else {
                    _uiState.value = ViewState.Success(result)
                }
            }
        }
    }

    fun update(item: Article) {
        viewModelScope.launch {
            repository.updateArticle(item)
        }
    }
}