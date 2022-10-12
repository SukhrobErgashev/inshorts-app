package dev.sukhrob.inshorts.presenter.fragments.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.inshorts.R
import dev.sukhrob.inshorts.data.local.Categories
import dev.sukhrob.inshorts.databinding.FragmentArticlesBinding
import dev.sukhrob.inshorts.presenter.adapters.ArticlesAdapter
import dev.sukhrob.inshorts.presenter.adapters.CategoryAdapter


@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter by lazy { CategoryAdapter() }
    private val articlesAdapter by lazy { ArticlesAdapter() }
    private val viewModel: ArticlesViewModel by viewModels<ArticlesViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        categoryAdapter.submitCategories(Categories.getAllCategory())

        setupArticlesList()
        //viewModel.loadArticlesByCategory("all")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.articles.observe(viewLifecycleOwner) {
            articlesAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        categoryAdapter.setItemClickListener {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            viewModel.loadArticlesByCategory(it)
        }

        articlesAdapter.bookmarkListener = {
            viewModel.update(it.apply {
                this.isBookmark = !isBookmark
            })
        }

        articlesAdapter.itemClickListener = {
            findNavController().navigate(
                ArticlesFragmentDirections.actionArticlesFragmentToDetailsFragment(
                    it
                )
            )
        }

        setupMenu()
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