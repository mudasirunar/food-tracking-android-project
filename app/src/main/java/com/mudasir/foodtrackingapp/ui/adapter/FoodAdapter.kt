package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.databinding.ItemFoodCardBinding
import java.util.Locale

import com.mudasir.foodtrackingapp.utils.formatPrice

class FoodAdapter(
    private var foods: List<FoodItem> = emptyList(),
    private val onFoodClick: (FoodItem) -> Unit,
    private val onAddToCartClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<FoodItem>) {
        foods = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount(): Int = foods.size

    inner class FoodViewHolder(private val binding: ItemFoodCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: FoodItem) {
            binding.tvFoodName.text = food.name
            binding.tvFoodPrice.text = food.price.formatPrice()
            binding.ivFoodImage.setImageResource(food.imageRes)

            binding.root.setOnClickListener {
                onFoodClick(food)
            }

            binding.btnAddToCart.setOnClickListener {
                onAddToCartClick(food)
            }
        }
    }
}
