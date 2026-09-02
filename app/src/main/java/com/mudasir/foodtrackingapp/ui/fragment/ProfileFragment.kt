package com.mudasir.foodtrackingapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.mudasir.foodtrackingapp.databinding.FragmentProfileBinding
import com.mudasir.foodtrackingapp.ui.activity.MainActivity
import com.mudasir.foodtrackingapp.ui.activity.OrderTrackingActivity
import com.mudasir.foodtrackingapp.ui.activity.SplashActivity
import com.mudasir.foodtrackingapp.utils.Constants

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeToggle()
        setupListeners()
    }

    private fun setupThemeToggle() {
        val sharedPref = requireActivity().getSharedPreferences(Constants.PREFS_SETTINGS, Context.MODE_PRIVATE)
        val hasSavedPref = sharedPref.contains(Constants.KEY_DARK_MODE)
        val isDarkMode = if (hasSavedPref) {
            sharedPref.getBoolean(Constants.KEY_DARK_MODE, false)
        } else {
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            currentNightMode == Configuration.UI_MODE_NIGHT_YES
        }
        binding.switchDarkMode.isChecked = isDarkMode

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean(Constants.KEY_DARK_MODE, isChecked).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupListeners() {
        binding.btnProfileBack.setOnClickListener {
            (activity as? MainActivity)?.navigateToHome()
        }

        binding.btnProfileMenu.setOnClickListener {
            Toast.makeText(requireContext(), "Profile Options Menu", Toast.LENGTH_SHORT).show()
        }

        binding.rowProfileDetails.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Ahmed Khan's Profile Details", Toast.LENGTH_SHORT).show()
        }

        binding.rowFoodTracking.setOnClickListener {
            val intent = Intent(requireContext(), OrderTrackingActivity::class.java)
            startActivity(intent)
        }

        binding.rowSettings.setOnClickListener {
            Toast.makeText(requireContext(), "Settings & Preferences", Toast.LENGTH_SHORT).show()
        }

        binding.rowLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log out") { _, _ ->
                    val intent = Intent(requireContext(), SplashActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
