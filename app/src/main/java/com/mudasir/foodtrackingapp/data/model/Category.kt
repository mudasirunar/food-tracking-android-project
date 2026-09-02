package com.mudasir.foodtrackingapp.data.model

data class Category(
    val id: String,
    val name: String,
    val iconRes: Int,
    var isSelected: Boolean = false
)
