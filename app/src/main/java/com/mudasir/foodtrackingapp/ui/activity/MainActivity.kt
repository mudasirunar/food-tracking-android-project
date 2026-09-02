package com.mudasir.foodtrackingapp.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import java.util.ArrayDeque

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    enum class NavTab(val tag: String) {
        HOME("HOME"),
        SEARCH("SEARCH"),
        CART("CART"),
        NOTIFICATIONS("NOTIFICATIONS"),
        PROFILE("PROFILE")
    }

    private var currentTab: NavTab = NavTab.HOME
    private val tabBackStack = ArrayDeque<NavTab>()
    private var backPressedTime: Long = 0

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

        setupBottomNavigation()
        setupBackNavigation()
        observeCartBadge()

        if (savedInstanceState == null) {
            switchTab(NavTab.HOME, addToHistory = false)
        } else {
            val savedTag = savedInstanceState.getString("ACTIVE_TAB", NavTab.HOME.name)
            currentTab = NavTab.valueOf(savedTag)
            updateNavSelection(currentTab)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("ACTIVE_TAB", currentTab.name)
    }

    private fun setupBottomNavigation() {
        binding.navHome.setOnClickListener {
            switchTab(NavTab.HOME)
        }

        binding.navSearch.setOnClickListener {
            switchTab(NavTab.SEARCH)
        }

        binding.navCart.setOnClickListener {
            switchTab(NavTab.CART)
        }

        binding.navNotifications.setOnClickListener {
            switchTab(NavTab.NOTIFICATIONS)
        }

        binding.navProfile.setOnClickListener {
            switchTab(NavTab.PROFILE)
        }
    }

    fun switchTab(tab: NavTab, addToHistory: Boolean = true) {
        if (currentTab == tab && supportFragmentManager.findFragmentByTag(tab.tag) != null) {
            return
        }

        if (addToHistory && currentTab != tab) {
            // Remove previous occurrence of tab to prevent loop cycles
            tabBackStack.remove(tab)
            tabBackStack.addLast(currentTab)
        }

        val transaction = supportFragmentManager.beginTransaction()

        // Hide all currently added fragments
        for (navItem in NavTab.values()) {
            val existing = supportFragmentManager.findFragmentByTag(navItem.tag)
            if (existing != null && existing.isAdded) {
                transaction.hide(existing)
            }
        }

        // Show or add the requested fragment
        var target = supportFragmentManager.findFragmentByTag(tab.tag)
        if (target == null) {
            target = createFragmentForTab(tab)
            transaction.add(R.id.navHostFragment, target, tab.tag)
        } else {
            transaction.show(target)
        }

        transaction.commit()
        currentTab = tab
        updateNavSelection(tab)
    }

    private fun createFragmentForTab(tab: NavTab): Fragment {
        return when (tab) {
            NavTab.HOME -> HomeFragment()
            NavTab.SEARCH -> SearchFragment()
            NavTab.CART -> CartFragment()
            NavTab.NOTIFICATIONS -> NotificationsFragment()
            NavTab.PROFILE -> ProfileFragment()
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

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackNavigation()
            }
        })
    }

    fun handleBackNavigation() {
        if (tabBackStack.isNotEmpty()) {
            val previousTab = tabBackStack.removeLast()
            switchTab(previousTab, addToHistory = false)
        } else if (currentTab != NavTab.HOME) {
            switchTab(NavTab.HOME, addToHistory = false)
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                finish()
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        }
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
        switchTab(NavTab.HOME)
    }

    fun navigateToSearch() {
        switchTab(NavTab.SEARCH)
    }

    fun navigateToCart() {
        switchTab(NavTab.CART)
    }

    fun navigateToNotifications() {
        switchTab(NavTab.NOTIFICATIONS)
    }

    fun navigateToProfile() {
        switchTab(NavTab.PROFILE)
    }
}
