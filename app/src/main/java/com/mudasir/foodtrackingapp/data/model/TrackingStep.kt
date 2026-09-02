package com.mudasir.foodtrackingapp.data.model

enum class StepState {
    COMPLETED,
    IN_PROGRESS,
    PENDING
}

data class TrackingStep(
    val id: Int,
    val title: String,
    val description: String,
    val state: StepState,
    val timestamp: String
)
