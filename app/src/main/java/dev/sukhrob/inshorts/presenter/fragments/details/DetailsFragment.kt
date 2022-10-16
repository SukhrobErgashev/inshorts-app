package dev.sukhrob.inshorts.presenter.fragments.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import coil.load
import dev.sukhrob.inshorts.databinding.FragmentDetailsBinding
import dev.sukhrob.inshorts.presenter.fragments.base.BaseFragment

class DetailsFragment: BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            image.load(args.article.imageUrl) {
                crossfade(600)
            }
            textTitle.text = args.article.title
            textAuthor.text = "by ${args.article.author}"
            textCategory.text = args.article.category
            textContent.text = args.article.content
        }

    }

}