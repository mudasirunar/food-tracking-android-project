package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.Category
import com.mudasir.foodtrackingapp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var categories: List<Category> = emptyList(),
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<Category>) {
        categories = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategoryName.text = category.name
            binding.ivCategoryIcon.setImageResource(category.iconRes)

            val context = binding.root.context
            if (category.isSelected) {
                binding.layoutCategoryIcon.setBackgroundResource(R.drawable.bg_category_selected)
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.purple_primary))
                binding.tvCategoryName.paint.isFakeBoldText = true
            } else {
                binding.layoutCategoryIcon.setBackgroundResource(R.drawable.bg_category_unselected)
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
                binding.tvCategoryName.paint.isFakeBoldText = false
            }

            binding.root.setOnClickListener {
                onCategoryClick(category)
            }
        }
    }
}
