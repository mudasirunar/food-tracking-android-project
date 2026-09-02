package com.mudasir.foodtrackingapp.data.repository

import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.AddOn
import com.mudasir.foodtrackingapp.data.model.Category
import com.mudasir.foodtrackingapp.data.model.FoodItem

class FoodRepository {

    fun getCategories(): List<Category> {
        return listOf(
            Category("all", "All", R.drawable.img_food_bowl_splash, isSelected = true),
            Category("burger", "Burger", R.drawable.img_beef_burger),
            Category("pizza", "Pizza", R.drawable.img_cheese_pizza),
            Category("chicken", "Chiken", R.drawable.img_fried_chicken),
            Category("noodles", "Noodles", R.drawable.img_noodles),
            Category("fries", "Fries", R.drawable.img_french_fries),
            Category("drinks", "Drinks", R.drawable.img_drink_boba),
            Category("deals", "Deals", R.drawable.img_combo_deal)
        )
    }

    private val burgerAddOns = listOf(
        AddOn("addon_cheese", "Cheese", 2.0, R.drawable.img_addon_cheese),
        AddOn("addon_sauce", "Sauce", 1.5, R.drawable.img_addon_sauce),
        AddOn("addon_dip", "Dip Sauce", 1.0, R.drawable.img_addon_dip),
        AddOn("addon_bacon", "Crispy Bacon", 2.5, R.drawable.img_addon_bacon)
    )

    private val pizzaAddOns = listOf(
        AddOn("addon_cheese", "Extra Mozzarella", 2.5, R.drawable.img_addon_cheese),
        AddOn("addon_sauce", "Garlic Butter Dip", 1.5, R.drawable.img_addon_dip),
        AddOn("addon_bacon", "Smoked Bacon", 2.0, R.drawable.img_addon_bacon)
    )

    private val chickenAddOns = listOf(
        AddOn("addon_dip", "Ranch Dip", 1.0, R.drawable.img_addon_dip),
        AddOn("addon_sauce", "Fiery Buffalo Sauce", 1.5, R.drawable.img_addon_sauce),
        AddOn("addon_cheese", "Melted Cheddar", 2.0, R.drawable.img_addon_cheese)
    )

    private val commonAddOns = listOf(
        AddOn("addon_cheese", "Cheese", 2.0, R.drawable.img_addon_cheese),
        AddOn("addon_sauce", "Special Sauce", 1.5, R.drawable.img_addon_sauce),
        AddOn("addon_dip", "Garlic Dip", 1.0, R.drawable.img_addon_dip)
    )

    private val foodCatalog = listOf(
        // Burgers
        FoodItem(
            id = "food_1",
            name = "Beef Burger",
            description = "Big juicy beef burger with cheese, lettuce, tomato, onions and special sauce",
            price = 10.0,
            rating = 4.8,
            categoryId = "burger",
            imageRes = R.drawable.img_beef_burger,
            calories = 650,
            proteinGrams = 34,
            carbsGrams = 42,
            fatGrams = 28,
            availableAddOns = burgerAddOns
        ),
        FoodItem(
            id = "food_b2",
            name = "Double Bacon Smash Burger",
            description = "Two smash beef patties, smoked bacon, double cheddar, caramelized onions, and secret relish",
            price = 14.0,
            rating = 4.9,
            categoryId = "burger",
            imageRes = R.drawable.img_beef_burger,
            calories = 890,
            proteinGrams = 52,
            carbsGrams = 38,
            fatGrams = 46,
            availableAddOns = burgerAddOns
        ),
        FoodItem(
            id = "food_b3",
            name = "Crispy Zinger Chicken Burger",
            description = "Crunchy spiced chicken breast filet, iceberg lettuce, melted cheese, and garlic mayo",
            price = 9.0,
            rating = 4.7,
            categoryId = "burger",
            imageRes = R.drawable.img_beef_burger,
            calories = 580,
            proteinGrams = 32,
            carbsGrams = 48,
            fatGrams = 22,
            availableAddOns = burgerAddOns
        ),

        // Pizzas
        FoodItem(
            id = "food_2",
            name = "Cheese Pizza",
            description = "Oven-baked crust topped with rich mozzarella cheese, fresh basil and savory marinara",
            price = 15.0,
            rating = 4.9,
            categoryId = "pizza",
            imageRes = R.drawable.img_cheese_pizza,
            calories = 820,
            proteinGrams = 32,
            carbsGrams = 78,
            fatGrams = 30,
            availableAddOns = pizzaAddOns
        ),
        FoodItem(
            id = "food_p2",
            name = "Pepperoni Supreme Pizza",
            description = "Loaded with Italian pepperoni, mozzarella, bell peppers, black olives, and oregano",
            price = 18.0,
            rating = 4.9,
            categoryId = "pizza",
            imageRes = R.drawable.img_cheese_pizza,
            calories = 940,
            proteinGrams = 40,
            carbsGrams = 84,
            fatGrams = 38,
            availableAddOns = pizzaAddOns
        ),
        FoodItem(
            id = "food_p3",
            name = "BBQ Chicken Pizza",
            description = "Tender grilled chicken chunks, smokey BBQ drizzle, red onions, and smoked gouda",
            price = 16.0,
            rating = 4.8,
            categoryId = "pizza",
            imageRes = R.drawable.img_cheese_pizza,
            calories = 780,
            proteinGrams = 38,
            carbsGrams = 72,
            fatGrams = 26,
            availableAddOns = pizzaAddOns
        ),

        // Chicken
        FoodItem(
            id = "food_3",
            name = "Fried Chicken",
            description = "Ultra crispy golden fried chicken drumsticks seasoned with secret herbs and spices",
            price = 8.0,
            rating = 4.7,
            categoryId = "chicken",
            imageRes = R.drawable.img_fried_chicken,
            calories = 540,
            proteinGrams = 38,
            carbsGrams = 18,
            fatGrams = 24,
            availableAddOns = chickenAddOns
        ),
        FoodItem(
            id = "food_c2",
            name = "Spicy Wings Bucket (8 pcs)",
            description = "Crispy coated chicken wings glazed in spicy Korean chili sauce with sesame sprinkle",
            price = 12.0,
            rating = 4.8,
            categoryId = "chicken",
            imageRes = R.drawable.img_fried_chicken,
            calories = 680,
            proteinGrams = 44,
            carbsGrams = 24,
            fatGrams = 32,
            availableAddOns = chickenAddOns
        ),
        FoodItem(
            id = "food_c3",
            name = "Crispy Chicken Tenders",
            description = "Pure chicken tenderloins breaded to perfection with honey mustard dip",
            price = 7.5,
            rating = 4.6,
            categoryId = "chicken",
            imageRes = R.drawable.img_fried_chicken,
            calories = 460,
            proteinGrams = 34,
            carbsGrams = 22,
            fatGrams = 18,
            availableAddOns = chickenAddOns
        ),

        // Noodles
        FoodItem(
            id = "food_4",
            name = "Noodles",
            description = "Steaming ramen noodles with fresh vegetables, egg, scallions and savory broth",
            price = 15.0,
            rating = 4.8,
            categoryId = "noodles",
            imageRes = R.drawable.img_noodles,
            calories = 490,
            proteinGrams = 22,
            carbsGrams = 64,
            fatGrams = 14,
            availableAddOns = commonAddOns
        ),
        FoodItem(
            id = "food_n2",
            name = "Spicy Teriyaki Udon",
            description = "Thick udon noodles tossed in sweet and savory teriyaki sauce, bok choy, and mushrooms",
            price = 13.0,
            rating = 4.7,
            categoryId = "noodles",
            imageRes = R.drawable.img_noodles,
            calories = 520,
            proteinGrams = 20,
            carbsGrams = 76,
            fatGrams = 12,
            availableAddOns = commonAddOns
        ),

        // Fries & Sides
        FoodItem(
            id = "food_5",
            name = "Crispy French Fries",
            description = "Hot golden salted french fries served fresh with dipping sauce",
            price = 5.0,
            rating = 4.6,
            categoryId = "fries",
            imageRes = R.drawable.img_french_fries,
            calories = 360,
            proteinGrams = 6,
            carbsGrams = 48,
            fatGrams = 16,
            availableAddOns = commonAddOns
        ),
        FoodItem(
            id = "food_f2",
            name = "Loaded Cheesy Bacon Fries",
            description = "Crisp french fries drenched in warm cheddar cheese sauce, bacon bits, and jalapeños",
            price = 8.0,
            rating = 4.9,
            categoryId = "fries",
            imageRes = R.drawable.img_french_fries,
            calories = 620,
            proteinGrams = 14,
            carbsGrams = 58,
            fatGrams = 34,
            availableAddOns = commonAddOns
        ),

        // Drinks
        FoodItem(
            id = "food_d1",
            name = "Iced Berry Boba Tea",
            description = "Refreshing chilled berry tea blend loaded with chewy brown sugar boba pearls",
            price = 6.0,
            rating = 4.8,
            categoryId = "drinks",
            imageRes = R.drawable.img_drink_boba,
            calories = 240,
            proteinGrams = 2,
            carbsGrams = 52,
            fatGrams = 1,
            availableAddOns = commonAddOns
        ),
        FoodItem(
            id = "food_d2",
            name = "Sparkling Citrus Lemonade",
            description = "Freshly squeezed lemons, mint leaves, and bubbly sparkling soda over ice",
            price = 4.5,
            rating = 4.7,
            categoryId = "drinks",
            imageRes = R.drawable.img_drink_boba,
            calories = 140,
            proteinGrams = 1,
            carbsGrams = 32,
            fatGrams = 0,
            availableAddOns = commonAddOns
        ),

        // Special Deals & Combos
        FoodItem(
            id = "food_deal1",
            name = "Mega Burger Feast Combo",
            description = "2 Beef Burgers + Large Box of Fries + 2 Iced Drinks at 30% discount!",
            price = 22.0,
            rating = 5.0,
            categoryId = "deals",
            imageRes = R.drawable.img_combo_deal,
            calories = 1450,
            proteinGrams = 68,
            carbsGrams = 140,
            fatGrams = 54,
            availableAddOns = burgerAddOns
        ),
        FoodItem(
            id = "food_deal2",
            name = "Pizza & Chicken Party Box",
            description = "1 Large Cheese Pizza + 6 Crispy Wings + Cheesy Dip + 2 Sodas",
            price = 28.0,
            rating = 4.9,
            categoryId = "deals",
            imageRes = R.drawable.img_combo_deal,
            calories = 1780,
            proteinGrams = 84,
            carbsGrams = 160,
            fatGrams = 68,
            availableAddOns = pizzaAddOns
        )
    )

    fun getAllFoods(): List<FoodItem> = foodCatalog

    fun getPopularFoods(): List<FoodItem> = foodCatalog.take(8)

    fun getDeals(): List<FoodItem> = foodCatalog.filter { it.categoryId == "deals" }

    fun getFoodsByCategory(categoryId: String): List<FoodItem> {
        if (categoryId == "all") return foodCatalog
        return foodCatalog.filter { it.categoryId.equals(categoryId, ignoreCase = true) }
    }

    fun getFoodById(id: String): FoodItem? {
        return foodCatalog.find { it.id == id }
    }

    fun searchFoods(query: String): List<FoodItem> {
        if (query.isBlank()) return emptyList()
        return foodCatalog.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.categoryId.contains(query, ignoreCase = true)
        }
    }
}
