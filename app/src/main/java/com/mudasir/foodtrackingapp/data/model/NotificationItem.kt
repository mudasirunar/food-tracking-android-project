package com.mudasir.foodtrackingapp.data.model

data class NotificationItem(
    val id: String,
    val senderName: String,
    val actionText: String,
    val timeAgo: String,
    var isUnread: Boolean,
    val avatarRes: Int
)
