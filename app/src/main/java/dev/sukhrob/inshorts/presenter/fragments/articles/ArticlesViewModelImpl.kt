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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModelImpl @Inject constructor(private val repository: InShortsRepository) :
    ArticlesViewModel, ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    private val _articles = MutableLiveData<List<Article>>()
    private val _error = MutableLiveData<String>()

    override val loading: LiveData<Boolean> get() = _loading
    override val articles: LiveData<List<Article>> get() = _articles
    override val error: LiveData<String> get() = _error

    override fun loadArticlesByCategory(category: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            repository.getArticlesByCategory(category).collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { data ->
                            _articles.postValue(data)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let { message ->
                            _error.postValue(message)
                        }
                    }
                }
            }
            _loading.postValue(false)
        }
    }

    override fun update(item: Article) {
        viewModelScope.launch {
            repository.updateArticle(item.toEntity())
        }
    }
}