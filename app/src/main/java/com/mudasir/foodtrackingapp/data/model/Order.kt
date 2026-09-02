package com.mudasir.foodtrackingapp.data.model

import java.io.Serializable

data class Order(
    val orderId: String,
    val items: List<CartItem>,
    val instructions: String,
    val subtotal: Double,
    val deliveryFee: Double = 0.0,
    val total: Double,
    val etaMinutes: String,
    val deliveryAddress: String,
    val driverName: String,
    val driverRating: String,
    val driverAvatarRes: Int,
    val totalCalories: Int,
    val totalProtein: Int,
    val totalCarbs: Int,
    val totalFat: Int
) : Serializable
