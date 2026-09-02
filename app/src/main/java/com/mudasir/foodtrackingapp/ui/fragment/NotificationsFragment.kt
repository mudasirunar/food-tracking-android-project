package com.mudasir.foodtrackingapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.repository.NotificationRepository
import com.mudasir.foodtrackingapp.databinding.FragmentNotificationsBinding
import com.mudasir.foodtrackingapp.ui.activity.MainActivity
import com.mudasir.foodtrackingapp.ui.adapter.NotificationAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val repository = NotificationRepository()
    private lateinit var notificationAdapter: NotificationAdapter
    private var isShowingUnreadOnly = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupTabs()
        setupListeners()
        loadNotifications()
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter { item ->
            Toast.makeText(requireContext(), "Opened notification from ${item.senderName}", Toast.LENGTH_SHORT).show()
        }
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    private fun setupTabs() {
        binding.tabAll.setOnClickListener {
            if (isShowingUnreadOnly) {
                isShowingUnreadOnly = false
                selectTab(isAllTab = true)
                loadNotifications()
            }
        }

        binding.tabUnread.setOnClickListener {
            if (!isShowingUnreadOnly) {
                isShowingUnreadOnly = true
                selectTab(isAllTab = false)
                loadNotifications()
            }
        }
    }

    private fun selectTab(isAllTab: Boolean) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.purple_primary)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.text_secondary)

        if (isAllTab) {
            binding.tvTabAll.setTextColor(activeColor)
            binding.tvTabAll.paint.isFakeBoldText = true
            binding.indicatorTabAll.visibility = View.VISIBLE

            binding.tvTabUnread.setTextColor(inactiveColor)
            binding.tvTabUnread.paint.isFakeBoldText = false
            binding.indicatorTabUnread.visibility = View.INVISIBLE
        } else {
            binding.tvTabUnread.setTextColor(activeColor)
            binding.tvTabUnread.paint.isFakeBoldText = true
            binding.indicatorTabUnread.visibility = View.VISIBLE

            binding.tvTabAll.setTextColor(inactiveColor)
            binding.tvTabAll.paint.isFakeBoldText = false
            binding.indicatorTabAll.visibility = View.INVISIBLE
        }
    }

    private fun loadNotifications() {
        val items = if (isShowingUnreadOnly) {
            repository.getUnreadNotifications()
        } else {
            repository.getAllNotifications()
        }
        notificationAdapter.submitList(items)
    }

    private fun setupListeners() {
        binding.btnNotificationsBack.setOnClickListener {
            (activity as? MainActivity)?.navigateToHome()
        }

        binding.btnNotificationsMenu.setOnClickListener {
            Toast.makeText(requireContext(), "Marking all notifications as read", Toast.LENGTH_SHORT).show()
            repository.getAllNotifications().forEach { it.isUnread = false }
            loadNotifications()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
