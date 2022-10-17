package dev.sukhrob.inshorts.presenter.fragments.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repo: InShortsRepository
): ViewModel() {

    private val _bookmarks = MutableStateFlow<List<Article>>(emptyList())
    private val _isEmpty = MutableSharedFlow<Boolean>()

    val bookmarks: StateFlow<List<Article>> get() = _bookmarks
    val isEmpty: SharedFlow<Boolean> get() = _isEmpty

    fun getBookmarks() {
        viewModelScope.launch {
            repo.getBookmarks().collect {
                if (it.isEmpty()) {
                    _isEmpty.tryEmit(true)
                } else {
                    _bookmarks.value = it
                }
            }
        }
    }

    fun update(item: Article) {
        viewModelScope.launch {
            repo.updateArticle(item)
        }
    }

}