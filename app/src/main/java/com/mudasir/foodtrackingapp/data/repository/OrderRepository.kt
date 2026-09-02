package com.mudasir.foodtrackingapp.data.repository

import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.CartItem
import com.mudasir.foodtrackingapp.data.model.Order
import com.mudasir.foodtrackingapp.data.model.StepState
import com.mudasir.foodtrackingapp.data.model.TrackingStep

class OrderRepository {

    fun createOrderFromCart(items: List<CartItem>, instructions: String): Order {
        val total = items.sumOf { it.totalPrice }
        return Order(
            orderId = "FT-${(1000..9999).random()}",
            items = items,
            instructions = instructions.ifBlank { "No special instructions provided" },
            subtotal = total,
            deliveryFee = 0.0,
            total = total,
            etaMinutes = "18 - 22 mins",
            deliveryAddress = "124 Sunset Boulevard, Apt 4B",
            driverName = "Tariq Mahmood",
            driverRating = "4.9 ★ (1,240 deliveries)",
            driverAvatarRes = R.drawable.img_rider_tariq,
            totalCalories = items.sumOf { it.foodItem.calories * it.quantity },
            totalProtein = items.sumOf { it.foodItem.proteinGrams * it.quantity },
            totalCarbs = items.sumOf { it.foodItem.carbsGrams * it.quantity },
            totalFat = items.sumOf { it.foodItem.fatGrams * it.quantity }
        )
    }

    fun getTrackingSteps(currentStepIndex: Int = 2): List<TrackingStep> {
        return listOf(
            TrackingStep(
                id = 1,
                title = "Order Placed",
                description = "We received your order and sent it to restaurant",
                state = if (currentStepIndex >= 1) StepState.COMPLETED else StepState.PENDING,
                timestamp = "06:15 PM"
            ),
            TrackingStep(
                id = 2,
                title = "Kitchen Preparing",
                description = "Chef is grilling patty and assembling fresh buns",
                state = if (currentStepIndex > 2) StepState.COMPLETED else if (currentStepIndex == 2) StepState.IN_PROGRESS else StepState.PENDING,
                timestamp = "06:22 PM"
            ),
            TrackingStep(
                id = 3,
                title = "On The Way",
                description = "Rider picked up order and is en-route",
                state = if (currentStepIndex > 3) StepState.COMPLETED else if (currentStepIndex == 3) StepState.IN_PROGRESS else StepState.PENDING,
                timestamp = "06:34 PM"
            ),
            TrackingStep(
                id = 4,
                title = "Delivered",
                description = "Enjoy your fresh and delicious meal!",
                state = if (currentStepIndex >= 4) StepState.COMPLETED else StepState.PENDING,
                timestamp = "06:45 PM"
            )
        )
    }
}
