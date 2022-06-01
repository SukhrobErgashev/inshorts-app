package dev.sukhrob.inshorts.presenter.fragments.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.domain.repository.InShortsRepository
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModelImpl @Inject constructor(private val repository: InShortsRepository) :
    ArticlesViewModel, ViewModel() {

    override val loadingLiveData: LiveData<Boolean> = repository.loadingState()
    override var articlesLiveData: MutableLiveData<List<Article>> = MutableLiveData()

    override fun loadArticlesByCategory(category: String) {
        articlesLiveData.value = repository.getArticlesByCategory(category)
        Log.d("TAG", category)
    }

    override fun update(item: Article) {
        repository.updateArticle(item)
    }
}