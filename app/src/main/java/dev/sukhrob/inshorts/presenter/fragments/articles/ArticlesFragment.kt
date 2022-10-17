package dev.sukhrob.inshorts.presenter.fragments.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.inshorts.R
import dev.sukhrob.inshorts.utils.Categories
import dev.sukhrob.inshorts.databinding.FragmentArticlesBinding
import dev.sukhrob.inshorts.presenter.adapters.ArticlesAdapter
import dev.sukhrob.inshorts.presenter.adapters.CategoryAdapter
import dev.sukhrob.inshorts.presenter.fragments.base.BaseFragment
import dev.sukhrob.inshorts.utils.hide
import dev.sukhrob.inshorts.utils.show
import dev.sukhrob.inshorts.utils.viewState.ViewState


@AndroidEntryPoint
class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val categoryAdapter by lazy { CategoryAdapter() }
    private val articlesAdapter by lazy { ArticlesAdapter() }
    private val viewModel: ArticlesViewModel by viewModels<ArticlesViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        categoryAdapter.submitCategories(Categories.getAllCategory())

        setupArticlesList()
        //viewModel.loadArticlesByCategory("all")

        setListeners()
        setupMenu()
        observeUiState()
    }

    private fun observeUiState() = lifecycleScope.launchWhenStarted {
        viewModel.uiState.collect { uiState ->
            when (uiState) {
                is ViewState.Error -> {
                    binding.progressBar.hide()
                }
                is ViewState.Loading -> {
                    binding.progressBar.show()
                }
                is ViewState.Success -> {
                    binding.progressBar.hide()
                    articlesAdapter.submitList(uiState.articles)
                }
                else -> Unit
            }
        }
    }

    private fun setListeners() {
        categoryAdapter.setItemClickListener {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            viewModel.loadArticlesByCategory(it)
        }

        articlesAdapter.bookmarkListener = { article, position ->
            viewModel.update(article.apply {
                this.isBookmark = !isBookmark
            })
            articlesAdapter.notifyItemChanged(position)
        }

        articlesAdapter.itemClickListener = {
            findNavController().navigate(
                ArticlesFragmentDirections.actionArticlesFragmentToDetailsFragment(
                    it
                )
            )
        }
    }

    private fun setupMenu() {
        /**
         *  We can tie the MenuProvider to the viewLifecycleOwner and optional
         *  Lifecycle.State.RESUMED to indicates when the menu should be visible.
         */
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu item here
                menuInflater.inflate(R.menu.bookmarks_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.bookmarks -> {
                        navigateToBookmarks()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupArticlesList() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
        }
    }

    private fun setupRecyclerView() {
        binding.horizontalRv.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = categoryAdapter
        }
    }

    private fun navigateToBookmarks() {
        findNavController().navigate(
            ArticlesFragmentDirections.actionArticlesFragmentToBookmarksFragment()
        )
    }

}