package com.mudasir.foodtrackingapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudasir.foodtrackingapp.databinding.FragmentCartBinding
import com.mudasir.foodtrackingapp.ui.activity.MainActivity
import com.mudasir.foodtrackingapp.ui.activity.OrderTrackingActivity
import com.mudasir.foodtrackingapp.ui.adapter.CartAdapter
import com.mudasir.foodtrackingapp.ui.viewmodel.CartViewModel
import com.mudasir.foodtrackingapp.utils.formatPrice

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrement = { item ->
                cartViewModel.increment(item.id)
            },
            onDecrement = { item ->
                cartViewModel.decrement(item.id)
            },
            onDelete = { item ->
                cartViewModel.removeItem(item.id)
                Toast.makeText(requireContext(), "${item.foodItem.name} removed from cart", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun setupListeners() {
        binding.btnCartBack.setOnClickListener {
            (activity as? MainActivity)?.handleBackNavigation()
        }

        binding.btnBackToMenu.setOnClickListener {
            (activity as? MainActivity)?.handleBackNavigation()
        }

        binding.btnClearCart.setOnClickListener {
            val items = cartViewModel.cartItems.value ?: emptyList()
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is already empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Clear Cart")
                .setMessage("Are you sure you want to remove all items from your cart?")
                .setPositiveButton("Clear All") { _, _ ->
                    cartViewModel.clearCart()
                    Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        binding.btnCheckout.setOnClickListener {
            val items = cartViewModel.cartItems.value ?: emptyList()
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty! Please add items first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val instructions = binding.etOrderInstructions.text.toString().trim()
            val intent = Intent(requireContext(), OrderTrackingActivity::class.java).apply {
                putExtra(OrderTrackingActivity.EXTRA_INSTRUCTIONS, instructions)
            }
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)
            if (items.isEmpty()) {
                binding.layoutEmptyCart.visibility = View.VISIBLE
                binding.rvCartItems.visibility = View.GONE
                binding.cardCartNutrition.visibility = View.GONE
                binding.btnCheckout.isEnabled = false
                binding.btnCheckout.alpha = 0.5f
                binding.tvCartItemCountBadge.visibility = View.GONE
                binding.btnClearCart.visibility = View.GONE
            } else {
                binding.layoutEmptyCart.visibility = View.GONE
                binding.rvCartItems.visibility = View.VISIBLE
                binding.cardCartNutrition.visibility = View.VISIBLE
                binding.btnCheckout.isEnabled = true
                binding.btnCheckout.alpha = 1.0f
                binding.tvCartItemCountBadge.visibility = View.VISIBLE
                binding.btnClearCart.visibility = View.VISIBLE
            }
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            binding.tvCartTotalPrice.text = total.formatPrice()
        }

        cartViewModel.totalCount.observe(viewLifecycleOwner) { count ->
            binding.tvCartItemCountBadge.text = "$count ${if (count == 1) "Item" else "Items"}"
        }

        cartViewModel.totalCalories.observe(viewLifecycleOwner) { calories ->
            binding.tvCartTotalCalories.text = "🔥 $calories kcal"
        }

        cartViewModel.totalProtein.observe(viewLifecycleOwner) { protein ->
            binding.tvCartTotalProtein.text = "💪 ${protein}g Pro"
        }

        cartViewModel.totalCarbs.observe(viewLifecycleOwner) { carbs ->
            binding.tvCartTotalCarbs.text = "🍞 ${carbs}g Carb"
        }

        cartViewModel.totalFat.observe(viewLifecycleOwner) { fat ->
            binding.tvCartTotalFat.text = "🥑 ${fat}g Fat"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
