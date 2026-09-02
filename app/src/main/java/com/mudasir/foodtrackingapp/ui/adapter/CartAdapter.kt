package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.data.model.CartItem
import com.mudasir.foodtrackingapp.databinding.ItemCartBinding
import java.util.Locale

import com.mudasir.foodtrackingapp.utils.formatPrice

class CartAdapter(
    private var cartItems: List<CartItem> = emptyList(),
    private val onIncrement: (CartItem) -> Unit,
    private val onDecrement: (CartItem) -> Unit,
    private val onDelete: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<CartItem>) {
        cartItems = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.tvCartFoodName.text = item.foodItem.name
            binding.tvCartFoodPrice.text = item.unitPrice.formatPrice()
            binding.tvQuantity.text = item.quantity.toString()
            binding.ivCartFoodImage.setImageResource(item.foodItem.imageRes)

            binding.btnIncrement.setOnClickListener {
                onIncrement(item)
            }

            binding.btnDecrement.setOnClickListener {
                onDecrement(item)
            }

            binding.ivDeleteCartItem.setOnClickListener {
                onDelete(item)
            }
        }
    }
}
