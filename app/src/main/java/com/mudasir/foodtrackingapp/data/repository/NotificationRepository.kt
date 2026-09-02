package com.mudasir.foodtrackingapp.data.repository

import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.NotificationItem

class NotificationRepository {

    private val notifications = listOf(
        NotificationItem("n1", "Ali Khan", "Replied to your message", "3m", true, R.drawable.img_avatar_ali),
        NotificationItem("n2", "Mubashir", "Commented on your post", "5m", true, R.drawable.img_avatar_mubashir),
        NotificationItem("n3", "Zeeshan", "Started a new chat", "20m", true, R.drawable.img_avatar_zeeshan),
        NotificationItem("n4", "Hassam", "Replied to your message", "50m", true, R.drawable.img_avatar_hassam),
        NotificationItem("n5", "Mariyam", "Liked your post", "1hr", false, R.drawable.img_avatar_mariyam),
        NotificationItem("n6", "Raheel", "Replied to your message", "12hr", false, R.drawable.img_avatar_ali),
        NotificationItem("n7", "Hasnain", "Messaged you", "20hr", false, R.drawable.img_avatar_mubashir),
        NotificationItem("n8", "Shayan", "Sent an attachment", "1day", false, R.drawable.img_avatar_zeeshan)
    )

    fun getAllNotifications(): List<NotificationItem> = notifications

    fun getUnreadNotifications(): List<NotificationItem> = notifications.filter { it.isUnread }
}
