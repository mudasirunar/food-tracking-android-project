package com.mudasir.foodtrackingapp.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.AddOn
import com.mudasir.foodtrackingapp.data.model.FoodItem
import com.mudasir.foodtrackingapp.data.repository.CartRepository
import com.mudasir.foodtrackingapp.data.repository.FoodRepository
import com.mudasir.foodtrackingapp.databinding.ActivityFoodDetailBinding
import com.mudasir.foodtrackingapp.ui.adapter.AddOnAdapter
import com.mudasir.foodtrackingapp.utils.formatPrice
import java.util.Locale

class FoodDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FOOD_ITEM = "extra_food_item"
    }

    private lateinit var binding: ActivityFoodDetailBinding
    private lateinit var addOnAdapter: AddOnAdapter

    private var foodItem: FoodItem? = null
    private var quantity: Int = 1
    private val selectedAddOns = mutableListOf<AddOn>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.btnBack.setPadding(
                binding.btnBack.paddingLeft,
                systemBars.top + 4,
                binding.btnBack.paddingRight,
                binding.btnBack.paddingBottom
            )
            binding.layoutBottomAction.setPadding(
                binding.layoutBottomAction.paddingLeft,
                binding.layoutBottomAction.paddingTop,
                binding.layoutBottomAction.paddingRight,
                systemBars.bottom + 12
            )
            insets
        }

        loadFoodItem()
        setupUI()
        setupAddOnsRecyclerView()
        setupListeners()
    }

    private fun loadFoodItem() {
        foodItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_FOOD_ITEM, FoodItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_FOOD_ITEM) as? FoodItem
        } ?: FoodRepository().getFoodById("food_1") // Default to Beef Burger if null
    }

    private fun setupUI() {
        foodItem?.let { food ->
            binding.ivDetailFoodImage.setImageResource(food.imageRes)
            binding.tvDetailFoodName.text = food.name
            binding.tvFoodRating.text = String.format(Locale.US, "%.1f", food.rating)
            binding.tvDetailFoodDescription.text = food.description
            binding.tvDetailQuantity.text = quantity.toString()

            // Nutrition tracking info
            binding.tvDetailCalories.text = "🔥 ${food.calories} kcal"
            binding.tvDetailProtein.text = "💪 ${food.proteinGrams}g Pro"
            binding.tvDetailCarbs.text = "🍞 ${food.carbsGrams}g Carb"
            binding.tvDetailFat.text = "🥑 ${food.fatGrams}g Fat"

            updatePriceDisplay()
        }
    }

    private fun setupAddOnsRecyclerView() {
        val addOns = foodItem?.availableAddOns ?: emptyList()
        addOnAdapter = AddOnAdapter(addOns) { addOn ->
            if (addOn.isSelected) {
                if (!selectedAddOns.contains(addOn)) selectedAddOns.add(addOn)
            } else {
                selectedAddOns.removeAll { it.id == addOn.id }
            }
            updatePriceDisplay()
        }
        binding.rvAddOns.apply {
            layoutManager = LinearLayoutManager(this@FoodDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = addOnAdapter
        }
    }

    private fun updatePriceDisplay() {
        val basePrice = foodItem?.price ?: 10.0
        val addOnTotal = selectedAddOns.sumOf { it.price }
        val unitPrice = basePrice + addOnTotal
        val total = unitPrice * quantity
        binding.tvDetailFoodPrice.text = total.formatPrice()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDetailIncrement.setOnClickListener {
            quantity++
            binding.tvDetailQuantity.text = quantity.toString()
            updatePriceDisplay()
        }

        binding.btnDetailDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvDetailQuantity.text = quantity.toString()
                updatePriceDisplay()
            }
        }

        binding.btnAddToCartCTA.setOnClickListener {
            foodItem?.let { food ->
                CartRepository.addItem(food, quantity, selectedAddOns.toList())
                Toast.makeText(this, "${food.name} (x$quantity) added to cart!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
