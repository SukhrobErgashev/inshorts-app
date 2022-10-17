package dev.sukhrob.inshorts.presenter.fragments.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.model.Resource
import dev.sukhrob.inshorts.domain.model.toEntity
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import dev.sukhrob.inshorts.utils.InternetConnection
import dev.sukhrob.inshorts.utils.viewState.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModelImpl @Inject constructor(private val repository: InShortsRepository) :
    ArticlesViewModel, ViewModel() {

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Empty)
    override val uiState: StateFlow<ViewState> get() = _uiState

    override fun loadArticlesByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = ViewState.Loading
            repository.loadNews(category, true).collect { result ->
                if (result.isNullOrEmpty()) {
                    _uiState.value = ViewState.Empty
                }
                else {
                    _uiState.value = ViewState.Success(result)
                }
            }
        }
    }

    override fun update(item: Article) {
        viewModelScope.launch {
            repository.updateArticle(item)
        }
    }
}

//viewModelScope.launch {
//    _loading.postValue(true)
//    repository.getArticlesByCategory(category).collect {
//        when (it) {
//            is Resource.Success -> {
//                it.data?.let { data ->
//                    _articles.postValue(data)
//                }
//            }
//            is Resource.Error -> {
//                it.message?.let { message ->
//                    _error.postValue(message)
//                }
//            }
//        }
//    }
//    _loading.postValue(false)
//}