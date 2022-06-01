package dev.sukhrob.inshorts.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.sukhrob.inshorts.R
import dev.sukhrob.inshorts.databinding.ItemArticleRowBinding
import dev.sukhrob.inshorts.domain.model.Article

class ArticlesAdapter : ListAdapter<Article, ArticlesAdapter.ArticlesViewHolder>(DiffCallback) {

    var bookmarkListener: ((Article) -> Unit)? = null
    var itemClickListener: ((Article) -> Unit)? = null

    class ArticlesViewHolder(private val binding: ItemArticleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.articleTitleRow.text = article.title
            binding.articleAuthorRow.text = article.author
            binding.articleImageRow.load(article.imageUrl) {
                crossfade(600)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticlesViewHolder {
                val binding = ItemArticleRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return ArticlesViewHolder(binding)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        return ArticlesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(getItem(position))
        }
        holder.itemView.findViewById<ImageView>(R.id.article_bookmark_row).setOnClickListener {
            bookmarkListener?.invoke(getItem(position))
        }
    }
}