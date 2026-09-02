package com.mudasir.foodtrackingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mudasir.foodtrackingapp.data.model.Order
import com.mudasir.foodtrackingapp.data.model.TrackingStep
import com.mudasir.foodtrackingapp.data.repository.CartRepository
import com.mudasir.foodtrackingapp.data.repository.OrderRepository

class OrderTrackingViewModel(
    private val repository: OrderRepository = OrderRepository()
) : ViewModel() {

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _steps = MutableLiveData<List<TrackingStep>>()
    val steps: LiveData<List<TrackingStep>> = _steps

    private val _currentStepIndex = MutableLiveData<Int>(2) // 2 = Kitchen Preparing
    val currentStepIndex: LiveData<Int> = _currentStepIndex

    fun initializeOrder(instructions: String = "") {
        val items = CartRepository.cartItems.value ?: emptyList()
        val createdOrder = repository.createOrderFromCart(items, instructions)
        _order.value = createdOrder
        _steps.value = repository.getTrackingSteps(_currentStepIndex.value ?: 2)
    }

    fun nextStep() {
        val next = ((_currentStepIndex.value ?: 1) + 1).coerceAtMost(4)
        _currentStepIndex.value = next
        _steps.value = repository.getTrackingSteps(next)
    }

    fun prevStep() {
        val prev = ((_currentStepIndex.value ?: 1) - 1).coerceAtLeast(1)
        _currentStepIndex.value = prev
        _steps.value = repository.getTrackingSteps(prev)
    }

    fun setStep(index: Int) {
        val step = index.coerceIn(1, 4)
        _currentStepIndex.value = step
        _steps.value = repository.getTrackingSteps(step)
    }
}
