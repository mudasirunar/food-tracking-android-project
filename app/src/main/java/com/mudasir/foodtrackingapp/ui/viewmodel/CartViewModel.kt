package com.mudasir.foodtrackingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mudasir.foodtrackingapp.data.model.AddOn
import com.mudasir.foodtrackingapp.data.model.CartItem
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.data.repository.CartRepository

class CartViewModel : ViewModel() {

    val cartItems: LiveData<List<CartItem>> = CartRepository.cartItems

    val totalPrice: LiveData<Double> = cartItems.map { items ->
        items.sumOf { it.totalPrice }
    }

    val totalCount: LiveData<Int> = cartItems.map { items ->
        items.sumOf { it.quantity }
    }

    val totalCalories: LiveData<Int> = cartItems.map { items ->
        items.sumOf { it.foodItem.calories * it.quantity }
    }

    val totalProtein: LiveData<Int> = cartItems.map { items ->
        items.sumOf { it.foodItem.proteinGrams * it.quantity }
    }

    val totalCarbs: LiveData<Int> = cartItems.map { items ->
        items.sumOf { it.foodItem.carbsGrams * it.quantity }
    }

    val totalFat: LiveData<Int> = cartItems.map { items ->
        items.sumOf { it.foodItem.fatGrams * it.quantity }
    }

    fun addToCart(foodItem: FoodItem, quantity: Int, selectedAddOns: List<AddOn> = emptyList()) {
        CartRepository.addItem(foodItem, quantity, selectedAddOns)
    }

    fun increment(cartItemId: String) {
        CartRepository.incrementQuantity(cartItemId)
    }

    fun decrement(cartItemId: String) {
        CartRepository.decrementQuantity(cartItemId)
    }

    fun removeItem(cartItemId: String) {
        CartRepository.removeItem(cartItemId)
    }

    fun clearCart() {
        CartRepository.clearCart()
    }
}
