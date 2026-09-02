package com.mudasir.foodtrackingapp.data.model

import java.io.Serializable

data class AddOn(
    val id: String,
    val name: String,
    val price: Double,
    val iconRes: Int,
    var isSelected: Boolean = false
) : Serializable
