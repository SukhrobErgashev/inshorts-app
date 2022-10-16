package dev.sukhrob.inshorts.presenter.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.inshorts.R
import dev.sukhrob.inshorts.databinding.ItemCategoriesBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var listener: ((String) -> Unit)? = null
    private var categories = ArrayList<String>()
    private var selectedItem: Int = -1

    fun setItemClickListener(l: (String) -> Unit) {
        this.listener = l
    }

    fun submitCategories(list: List<String>) {
        this.categories.clear()
        this.categories.addAll(list)
    }

    class CategoryViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(string: String, selectedItem: Int) {
            binding.textCategory.text = string

            // if item selected then change it's state color
            when (selectedItem) {
                adapterPosition -> {
                    binding.textCategory.setBackgroundResource(R.drawable.shape_category_selected)
                    binding.textCategory.setTextColor(Color.parseColor("#ffffff"))
                }
                else -> {
                    binding.textCategory.setBackgroundResource(R.drawable.shape_category_default)
                    binding.textCategory.setTextColor(Color.parseColor("#FF6861"))
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val binding = ItemCategoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CategoryViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], selectedItem)
        holder.itemView.setOnClickListener {
            listener?.invoke(categories[position])

            if (selectedItem == position) {
                notifyItemChanged(position)
                return@setOnClickListener
            }

            selectedItem = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = categories.size

}