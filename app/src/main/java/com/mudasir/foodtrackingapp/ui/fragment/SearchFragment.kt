package com.mudasir.foodtrackingapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.databinding.FragmentSearchBinding
import com.mudasir.foodtrackingapp.ui.activity.FoodDetailActivity
import com.mudasir.foodtrackingapp.ui.activity.MainActivity
import com.mudasir.foodtrackingapp.ui.adapter.FoodAdapter
import com.mudasir.foodtrackingapp.ui.viewmodel.CartViewModel
import com.mudasir.foodtrackingapp.ui.viewmodel.FoodViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val foodViewModel: FoodViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var searchResultsAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchResultsRecyclerView()
        setupSearchInput()
        setupListeners()
        observeViewModel()
    }

    private fun setupSearchResultsRecyclerView() {
        searchResultsAdapter = FoodAdapter(
            onFoodClick = { food ->
                openFoodDetail(food)
            },
            onAddToCartClick = { food ->
                cartViewModel.addToCart(food, 1)
            }
        )
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = searchResultsAdapter
        }
    }

    private fun setupSearchInput() {
        binding.etSearchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                foodViewModel.search(query)
                if (query.isNotEmpty()) {
                    binding.layoutRecentQueriesSection.visibility = View.GONE
                    binding.rvSearchResults.visibility = View.VISIBLE
                } else {
                    binding.layoutRecentQueriesSection.visibility = View.VISIBLE
                    binding.rvSearchResults.visibility = View.GONE
                    binding.tvNoResults.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSearchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearchInput.text.toString().trim()
                if (query.isNotEmpty()) {
                    foodViewModel.addRecentQuery(query)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupListeners() {
        binding.btnSearchCancel.setOnClickListener {
            if (binding.etSearchInput.text.isNotEmpty()) {
                binding.etSearchInput.text.clear()
            } else {
                (activity as? MainActivity)?.navigateToHome()
            }
        }

        binding.btnClearRecentQueries.setOnClickListener {
            foodViewModel.clearRecentQueries()
        }
    }

    private fun observeViewModel() {
        foodViewModel.recentQueries.observe(viewLifecycleOwner) { queries ->
            binding.chipGroupRecent.removeAllViews()
            queries.forEach { query ->
                val chip = Chip(requireContext()).apply {
                    text = query
                    isClickable = true
                    isCheckable = false
                    setChipBackgroundColorResource(R.color.bg_chip)
                    setTextColor(resources.getColor(R.color.text_primary, null))
                    setOnClickListener {
                        binding.etSearchInput.setText(query)
                        binding.etSearchInput.setSelection(query.length)
                        foodViewModel.addRecentQuery(query)
                    }
                }
                binding.chipGroupRecent.addView(chip)
            }
        }

        foodViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            searchResultsAdapter.submitList(results)
            val query = binding.etSearchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                if (results.isEmpty()) {
                    binding.tvNoResults.visibility = View.VISIBLE
                    binding.rvSearchResults.visibility = View.GONE
                } else {
                    binding.tvNoResults.visibility = View.GONE
                    binding.rvSearchResults.visibility = View.VISIBLE
                }
            }
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
