package com.mudasir.foodtrackingapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.AddOn
import com.mudasir.foodtrackingapp.data.model.CartItem
import com.mudasir.foodtrackingapp.data.model.FoodItem

object CartRepository {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val currentList = mutableListOf<CartItem>()

    init {
        // Seed default cart items from Figma design Screen 4 (Noodles $15 + Beef Burger $10 = $25)
        val noodles = FoodItem(
            id = "food_4",
            name = "Noodles",
            description = "Steaming ramen noodles with fresh vegetables, egg, scallions and savory broth",
            price = 15.0,
            rating = 4.8,
            categoryId = "noodles",
            imageRes = R.drawable.img_noodles,
            calories = 490,
            proteinGrams = 22,
            carbsGrams = 64,
            fatGrams = 14
        )

        val beefBurger = FoodItem(
            id = "food_1",
            name = "Beef Burger",
            description = "Big juicy beef burger with cheese, lettuce, tomato, onions and special sauce",
            price = 10.0,
            rating = 4.8,
            categoryId = "burger",
            imageRes = R.drawable.img_beef_burger,
            calories = 650,
            proteinGrams = 34,
            carbsGrams = 42,
            fatGrams = 28
        )

        currentList.add(CartItem(id = "cart_1", foodItem = noodles, quantity = 1))
        currentList.add(CartItem(id = "cart_2", foodItem = beefBurger, quantity = 1))
        _cartItems.value = currentList.toList()
    }

    fun addItem(foodItem: FoodItem, quantity: Int, selectedAddOns: List<AddOn> = emptyList()) {
        val existingIndex = currentList.indexOfFirst {
            it.foodItem.id == foodItem.id && it.selectedAddOns == selectedAddOns
        }
        if (existingIndex != -1) {
            val existing = currentList[existingIndex]
            currentList[existingIndex] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            val newItem = CartItem(
                id = "cart_${System.currentTimeMillis()}",
                foodItem = foodItem,
                quantity = quantity,
                selectedAddOns = selectedAddOns
            )
            currentList.add(newItem)
        }
        _cartItems.value = currentList.toList()
    }

    fun incrementQuantity(cartItemId: String) {
        val index = currentList.indexOfFirst { it.id == cartItemId }
        if (index != -1) {
            val item = currentList[index]
            currentList[index] = item.copy(quantity = item.quantity + 1)
            _cartItems.value = currentList.toList()
        }
    }

    fun decrementQuantity(cartItemId: String) {
        val index = currentList.indexOfFirst { it.id == cartItemId }
        if (index != -1) {
            val item = currentList[index]
            if (item.quantity > 1) {
                currentList[index] = item.copy(quantity = item.quantity - 1)
            } else {
                currentList.removeAt(index)
            }
            _cartItems.value = currentList.toList()
        }
    }

    fun removeItem(cartItemId: String) {
        val removed = currentList.removeAll { it.id == cartItemId }
        if (removed) {
            _cartItems.value = currentList.toList()
        }
    }

    fun clearCart() {
        currentList.clear()
        _cartItems.value = emptyList()
    }

    fun getTotal(): Double {
        return currentList.sumOf { it.totalPrice }
    }

    fun getItemCount(): Int {
        return currentList.sumOf { it.quantity }
    }

    fun getTotalCalories(): Int {
        return currentList.sumOf { it.foodItem.calories * it.quantity }
    }

    fun getTotalProtein(): Int {
        return currentList.sumOf { it.foodItem.proteinGrams * it.quantity }
    }

    fun getTotalCarbs(): Int {
        return currentList.sumOf { it.foodItem.carbsGrams * it.quantity }
    }

    fun getTotalFat(): Int {
        return currentList.sumOf { it.foodItem.fatGrams * it.quantity }
    }
}
