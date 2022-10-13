package dev.sukhrob.inshorts.presenter.fragments.bookmarks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dev.sukhrob.inshorts.databinding.FragmentBookmarksBinding
import dev.sukhrob.inshorts.presenter.fragments.base.BaseFragment

class BookmarksFragment: BaseFragment<FragmentBookmarksBinding>(FragmentBookmarksBinding::inflate) {

    private val viewModel: BookmarksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBookmarks()
    }

}