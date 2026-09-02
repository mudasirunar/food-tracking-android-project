package com.mudasir.foodtrackingapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.data.repository.FoodRepository
import com.mudasir.foodtrackingapp.databinding.FragmentHomeBinding
import com.mudasir.foodtrackingapp.ui.activity.FoodDetailActivity
import com.mudasir.foodtrackingapp.ui.activity.MainActivity
import com.mudasir.foodtrackingapp.ui.adapter.CategoryAdapter
import com.mudasir.foodtrackingapp.ui.adapter.FoodAdapter
import com.mudasir.foodtrackingapp.ui.viewmodel.CartViewModel
import com.mudasir.foodtrackingapp.ui.viewmodel.FoodViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foodViewModel: FoodViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var dealsAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoriesRecyclerView()
        setupPopularFoodsRecyclerView()
        setupDealsRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupCategoriesRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            foodViewModel.selectCategory(category.id)
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupPopularFoodsRecyclerView() {
        foodAdapter = FoodAdapter(
            onFoodClick = { food ->
                openFoodDetail(food)
            },
            onAddToCartClick = { food ->
                cartViewModel.addToCart(food, 1)
                Toast.makeText(requireContext(), "${food.name} added to cart!", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvPopularFoods.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = foodAdapter
        }
    }

    private fun setupDealsRecyclerView() {
        dealsAdapter = FoodAdapter(
            onFoodClick = { food ->
                openFoodDetail(food)
            },
            onAddToCartClick = { food ->
                cartViewModel.addToCart(food, 1)
                Toast.makeText(requireContext(), "${food.name} deal added to cart!", Toast.LENGTH_SHORT).show()
            }
        )
        binding.rvDeals.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dealsAdapter
        }
    }

    private fun setupListeners() {
        binding.layoutSearchBar.setOnClickListener {
            (activity as? MainActivity)?.navigateToSearch()
        }

        binding.cardPromotion.setOnClickListener {
            val deal = FoodRepository().getDeals().firstOrNull()
            if (deal != null) {
                openFoodDetail(deal)
            } else {
                Toast.makeText(requireContext(), "Promo Offer: Free Box of Fries on orders > $150!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        foodViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.submitList(categories)
        }

        foodViewModel.selectedCategory.observe(viewLifecycleOwner) { categoryId ->
            val catName = foodViewModel.categories.value?.find { it.id == categoryId }?.name ?: "Popular"
            binding.tvSectionHeader.text = if (categoryId == "all") "Popular" else "$catName Menu"
        }

        foodViewModel.popularFoods.observe(viewLifecycleOwner) { foods ->
            foodAdapter.submitList(foods)
            binding.tvItemCountLabel.text = "${foods.size} items available"
        }

        foodViewModel.deals.observe(viewLifecycleOwner) { deals ->
            dealsAdapter.submitList(deals)
            binding.tvDealsHeader.visibility = if (deals.isEmpty()) View.GONE else View.VISIBLE
            binding.rvDeals.visibility = if (deals.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun openFoodDetail(food: FoodItem) {
        val intent = Intent(requireContext(), FoodDetailActivity::class.java).apply {
            putExtra(FoodDetailActivity.EXTRA_FOOD_ITEM, food)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
