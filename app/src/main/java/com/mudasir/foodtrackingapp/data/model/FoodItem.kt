package com.mudasir.foodtrackingapp.data.model

import java.io.Serializable

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val categoryId: String,
    val imageRes: Int,
    val calories: Int,
    val proteinGrams: Int,
    val carbsGrams: Int,
    val fatGrams: Int,
    val availableAddOns: List<AddOn> = emptyList()
) : Serializable
