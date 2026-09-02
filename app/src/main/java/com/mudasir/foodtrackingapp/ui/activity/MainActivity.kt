package com.mudasir.foodtrackingapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.repository.CartRepository
import com.mudasir.foodtrackingapp.databinding.ActivityMainBinding
import com.mudasir.foodtrackingapp.ui.fragment.CartFragment
import com.mudasir.foodtrackingapp.ui.fragment.HomeFragment
import com.mudasir.foodtrackingapp.ui.fragment.NotificationsFragment
import com.mudasir.foodtrackingapp.ui.fragment.ProfileFragment
import com.mudasir.foodtrackingapp.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val cartFragment = CartFragment()
    private val notificationsFragment = NotificationsFragment()
    private val profileFragment = ProfileFragment()

    private var activeFragment: Fragment = homeFragment

    enum class NavTab {
        HOME, SEARCH, CART, NOTIFICATIONS, PROFILE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.navHostFragment.setPadding(0, systemBars.top, 0, 0)
            binding.layoutBottomNav.setPadding(
                binding.layoutBottomNav.paddingLeft,
                binding.layoutBottomNav.paddingTop,
                binding.layoutBottomNav.paddingRight,
                systemBars.bottom
            )
            insets
        }

        setupFragments(savedInstanceState)
        setupBottomNavigation()
        observeCartBadge()
    }

    private fun setupFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.navHostFragment, profileFragment, "PROFILE").hide(profileFragment)
                .add(R.id.navHostFragment, notificationsFragment, "NOTIFICATIONS").hide(notificationsFragment)
                .add(R.id.navHostFragment, cartFragment, "CART").hide(cartFragment)
                .add(R.id.navHostFragment, searchFragment, "SEARCH").hide(searchFragment)
                .add(R.id.navHostFragment, homeFragment, "HOME")
                .commit()
            activeFragment = homeFragment
            updateNavSelection(NavTab.HOME)
        }
    }

    private fun setupBottomNavigation() {
        binding.navHome.setOnClickListener {
            switchFragment(homeFragment, NavTab.HOME)
        }

        binding.navSearch.setOnClickListener {
            switchFragment(searchFragment, NavTab.SEARCH)
        }

        binding.navCart.setOnClickListener {
            switchFragment(cartFragment, NavTab.CART)
        }

        binding.navNotifications.setOnClickListener {
            switchFragment(notificationsFragment, NavTab.NOTIFICATIONS)
        }

        binding.navProfile.setOnClickListener {
            switchFragment(profileFragment, NavTab.PROFILE)
        }
    }

    private fun switchFragment(targetFragment: Fragment, tab: NavTab) {
        if (activeFragment != targetFragment) {
            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(targetFragment)
                .commit()
            activeFragment = targetFragment
            updateNavSelection(tab)
        }
    }

    private fun updateNavSelection(tab: NavTab) {
        val selectedTint = ContextCompat.getColor(this, R.color.white)
        val unselectedTint = ContextCompat.getColor(this, R.color.text_white_secondary)

        binding.ivNavHome.setColorFilter(if (tab == NavTab.HOME) selectedTint else unselectedTint)
        binding.ivNavSearch.setColorFilter(if (tab == NavTab.SEARCH) selectedTint else unselectedTint)
        binding.ivNavCart.setColorFilter(if (tab == NavTab.CART) selectedTint else unselectedTint)
        binding.ivNavNotifications.setColorFilter(if (tab == NavTab.NOTIFICATIONS) selectedTint else unselectedTint)
        binding.ivNavProfile.setColorFilter(if (tab == NavTab.PROFILE) selectedTint else unselectedTint)

        binding.layoutNavCartPill.alpha = if (tab == NavTab.CART) 1.0f else 0.75f
    }

    private fun observeCartBadge() {
        CartRepository.cartItems.observe(this) { items ->
            val count = items.sumOf { it.quantity }
            if (count > 0) {
                binding.tvNavCartBadge.visibility = View.VISIBLE
                binding.tvNavCartBadge.text = count.toString()
            } else {
                binding.tvNavCartBadge.visibility = View.GONE
            }
        }
    }

    fun navigateToHome() {
        switchFragment(homeFragment, NavTab.HOME)
    }

    fun navigateToSearch() {
        switchFragment(searchFragment, NavTab.SEARCH)
    }

    fun navigateToCart() {
        switchFragment(cartFragment, NavTab.CART)
    }

    fun navigateToNotifications() {
        switchFragment(notificationsFragment, NavTab.NOTIFICATIONS)
    }

    fun navigateToProfile() {
        switchFragment(profileFragment, NavTab.PROFILE)
    }
}
