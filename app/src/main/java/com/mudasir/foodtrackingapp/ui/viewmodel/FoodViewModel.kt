package com.mudasir.foodtrackingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mudasir.foodtrackingapp.data.model.Category
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.data.repository.FoodRepository

class FoodViewModel(private val repository: FoodRepository = FoodRepository()) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _popularFoods = MutableLiveData<List<FoodItem>>()
    val popularFoods: LiveData<List<FoodItem>> = _popularFoods

    private val _deals = MutableLiveData<List<FoodItem>>()
    val deals: LiveData<List<FoodItem>> = _deals

    private val _selectedCategory = MutableLiveData<String>("all")
    val selectedCategory: LiveData<String> = _selectedCategory

    private val _searchResults = MutableLiveData<List<FoodItem>>()
    val searchResults: LiveData<List<FoodItem>> = _searchResults

    private val _recentQueries = MutableLiveData<List<String>>()
    val recentQueries: LiveData<List<String>> = _recentQueries

    init {
        loadData()
    }

    private fun loadData() {
        _categories.value = repository.getCategories()
        _popularFoods.value = repository.getAllFoods()
        _deals.value = repository.getDeals()
        _recentQueries.value = listOf("Beef Burger", "Cheese Pizza", "Double Bacon", "Crispy Wings", "Iced Berry Boba")
    }

    fun selectCategory(categoryId: String) {
        _selectedCategory.value = categoryId
        val updatedCategories = _categories.value?.map {
            it.copy(isSelected = it.id.equals(categoryId, ignoreCase = true))
        }
        _categories.value = updatedCategories ?: emptyList()
        _popularFoods.value = repository.getFoodsByCategory(categoryId)
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = repository.searchFoods(query)
        }
    }

    fun addRecentQuery(query: String) {
        if (query.isNotBlank()) {
            val current = _recentQueries.value?.toMutableList() ?: mutableListOf()
            current.remove(query)
            current.add(0, query)
            _recentQueries.value = current.take(6)
        }
    }

    fun clearRecentQueries() {
        _recentQueries.value = emptyList()
    }
}
