package dev.sukhrob.inshorts.presenter.fragments.bookmarks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.inshorts.databinding.FragmentBookmarksBinding
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.presenter.adapters.ArticlesAdapter
import dev.sukhrob.inshorts.presenter.fragments.articles.ArticlesViewModel
import dev.sukhrob.inshorts.presenter.fragments.base.BaseFragment

@AndroidEntryPoint
class BookmarksFragment :
    BaseFragment<FragmentBookmarksBinding>(FragmentBookmarksBinding::inflate) {

    private val viewModel: BookmarksViewModel by viewModels()
    private val adapter: ArticlesAdapter by lazy { ArticlesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setupRecyclerView()
        setListeners()

        viewModel.getBookmarks()
    }

    private val bookmarksObserver = Observer<List<Article>> {
        adapter.submitList(it)
    }

    private fun setObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner, bookmarksObserver)
    }

    private fun setListeners() {
        adapter.bookmarkListener = { article, position ->
            viewModel.update(article.apply {
                this.isBookmark = !isBookmark
            })
            //adapter.notifyItemChanged(position)
        }

        adapter.itemClickListener = {
            findNavController().navigate(
                BookmarksFragmentDirections.actionBookmarksFragmentToDetailsFragment(it)
            )
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvBookmarks) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BookmarksFragment.adapter
        }
    }

}