package dev.sukhrob.inshorts.presenter.fragments.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        setHasOptionsMenu(true)

        setupRecyclerView()
        categoryAdapter.submitCategories(Categories.getAllCategory())


        setupArticlesList()

        //viewModel.loadArticlesByCategory("all")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.articlesLiveData.observe(viewLifecycleOwner) {
            Log.d("TTT", "onViewCreated: ${it.size}")
            articlesAdapter.submitList(it)
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
            findNavController().navigate(ArticlesFragmentDirections.actionArticlesFragmentToDetailsFragment(it))
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bookmarks_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.bookmarks) {
            findNavController().navigate(ArticlesFragmentDirections.actionArticlesFragmentToBookmarksFragment())
        }
        return super.onOptionsItemSelected(item)
    }

}