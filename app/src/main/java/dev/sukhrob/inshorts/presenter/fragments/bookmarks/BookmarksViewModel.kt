package dev.sukhrob.inshorts.presenter.fragments.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repo: InShortsRepository
): ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<String>()
    private var _bookmarks = MutableLiveData<List<Article>>()

    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error
    val bookmarks: LiveData<List<Article>> get() = _bookmarks

    fun getBookmarks() {
        viewModelScope.launch {
            _loading.postValue(true)
            repo.getBookmarks().collect {
                if (it.isEmpty()) {
                    _bookmarks.postValue(it)
                } else {
                    _error.postValue("There are no bookmarks yet!")
                }
            }
            _loading.postValue(false)
        }
    }

}