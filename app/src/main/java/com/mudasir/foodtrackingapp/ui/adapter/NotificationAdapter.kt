package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.NotificationItem
import com.mudasir.foodtrackingapp.databinding.ItemNotificationBinding

class NotificationAdapter(
    private var notifications: List<NotificationItem> = emptyList(),
    private val onNotificationClick: (NotificationItem) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<NotificationItem>) {
        notifications = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationItem) {
            val context = binding.root.context
            binding.tvSenderName.text = item.senderName
            binding.tvNotificationAction.text = item.actionText
            binding.tvTimeAgo.text = item.timeAgo
            binding.ivSenderAvatar.setImageResource(item.avatarRes)

            if (item.isUnread) {
                binding.cardNotification.setCardBackgroundColor(ContextCompat.getColor(context, R.color.notification_unread_bg))
                binding.viewUnreadDot.visibility = View.VISIBLE
            } else {
                binding.cardNotification.setCardBackgroundColor(ContextCompat.getColor(context, R.color.notification_read_bg))
                binding.viewUnreadDot.visibility = View.INVISIBLE
            }

            binding.root.setOnClickListener {
                item.isUnread = false
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    notifyItemChanged(pos)
                }
                onNotificationClick(item)
            }
        }
    }
}
