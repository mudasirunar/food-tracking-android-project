package com.mudasir.foodtrackingapp.data.model

import java.io.Serializable

data class CartItem(
    val id: String,
    val foodItem: FoodItem,
    var quantity: Int,
    val selectedAddOns: List<AddOn> = emptyList()
) : Serializable {
    val unitPrice: Double
        get() = foodItem.price + selectedAddOns.sumOf { it.price }

    val totalPrice: Double
        get() = unitPrice * quantity
}
