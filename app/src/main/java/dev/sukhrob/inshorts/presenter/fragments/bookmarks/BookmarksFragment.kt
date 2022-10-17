package dev.sukhrob.inshorts.presenter.fragments.bookmarks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.inshorts.databinding.FragmentBookmarksBinding
import dev.sukhrob.inshorts.domain.model.Article
import dev.sukhrob.inshorts.presenter.adapters.ArticlesAdapter
import dev.sukhrob.inshorts.presenter.fragments.base.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarksFragment :
    BaseFragment<FragmentBookmarksBinding>(FragmentBookmarksBinding::inflate) {

    private val viewModel: BookmarksViewModel by viewModels()
    private val adapter: ArticlesAdapter by lazy { ArticlesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFlows()
        setupRecyclerView()
        setListeners()

        viewModel.getBookmarks()
    }

    private fun observeFlows() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.bookmarks.collect { bookmarks ->
                    adapter.submitList(bookmarks)
                }
            }
            launch {
                viewModel.isEmpty.collect { isEmpty ->
                    if (isEmpty)
                        Snackbar.make(binding.root, "There is no data!", 3000).show()
                }
            }
        }
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