package com.mudasir.foodtrackingapp.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mudasir.foodtrackingapp.databinding.ActivityOrderTrackingBinding
import com.mudasir.foodtrackingapp.ui.adapter.OrderTrackingStepAdapter
import com.mudasir.foodtrackingapp.ui.viewmodel.OrderTrackingViewModel

class OrderTrackingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INSTRUCTIONS = "extra_instructions"
    }

    private lateinit var binding: ActivityOrderTrackingBinding
    private val viewModel: OrderTrackingViewModel by viewModels()
    private lateinit var stepAdapter: OrderTrackingStepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.trackingAppBar.setPadding(
                binding.trackingAppBar.paddingLeft,
                systemBars.top + 10,
                binding.trackingAppBar.paddingRight,
                14
            )
            binding.layoutTrackingBottomCTA.setPadding(
                binding.layoutTrackingBottomCTA.paddingLeft,
                10,
                binding.layoutTrackingBottomCTA.paddingRight,
                systemBars.bottom + 12
            )
            insets
        }

        val instructions = intent.getStringExtra(EXTRA_INSTRUCTIONS) ?: ""
        viewModel.initializeOrder(instructions)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        stepAdapter = OrderTrackingStepAdapter { step, stepPosition ->
            viewModel.setStep(stepPosition)
            Toast.makeText(this, "Jumped to: ${step.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvTrackingSteps.apply {
            layoutManager = LinearLayoutManager(this@OrderTrackingActivity)
            adapter = stepAdapter
        }
    }

    private fun setupListeners() {
        binding.btnTrackingBack.setOnClickListener {
            finish()
        }

        binding.btnCallRider.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+923001234567")
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Calling Tariq Mahmood (+92 300 1234567)", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMessageRider.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:+923001234567")
                putExtra("sms_body", "Hello, I am tracking my food order.")
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Messaging Tariq Mahmood", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSimulatePrevStep.setOnClickListener {
            val currentIndex = viewModel.currentStepIndex.value ?: 2
            if (currentIndex > 1) {
                viewModel.prevStep()
                val newIndex = viewModel.currentStepIndex.value ?: 1
                val message = when (newIndex) {
                    1 -> "Status: Order Placed"
                    2 -> "Status: Kitchen Preparing"
                    3 -> "Status: On The Way"
                    else -> "Status updated"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Already at first status (Order Placed)", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSimulateNextStep.setOnClickListener {
            val currentIndex = viewModel.currentStepIndex.value ?: 1
            if (currentIndex < 4) {
                viewModel.nextStep()
                val nextIndex = viewModel.currentStepIndex.value ?: 1
                val message = when (nextIndex) {
                    2 -> "Kitchen is preparing your meal!"
                    3 -> "Rider picked up your food and is on the way!"
                    4 -> "🎉 Order Delivered! Enjoy your food!"
                    else -> "Status updated"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Order already delivered! Thank you for ordering.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.order.observe(this) { order ->
            binding.tvOrderId.text = "Order #${order.orderId}"
            binding.tvDeliveryAddress.text = "Delivering to: ${order.deliveryAddress}"
            binding.tvRiderName.text = order.driverName
            binding.tvRiderRating.text = order.driverRating
            binding.ivRiderAvatar.setImageResource(order.driverAvatarRes)

            // Nutrition & Calorie Tracking Stats
            binding.tvTotalCalories.text = "${order.totalCalories} kcal"
            binding.tvTotalProtein.text = "${order.totalProtein}g"
            binding.tvTotalCarbs.text = "${order.totalCarbs}g"
            binding.tvTotalFat.text = "${order.totalFat}g"
        }

        viewModel.steps.observe(this) { steps ->
            stepAdapter.submitList(steps)
        }

        viewModel.currentStepIndex.observe(this) { stepIndex ->
            binding.btnSimulatePrevStep.isEnabled = stepIndex > 1
            binding.btnSimulatePrevStep.alpha = if (stepIndex > 1) 1.0f else 0.4f

            when (stepIndex) {
                1 -> {
                    binding.tvLiveETA.text = "25 - 30 mins"
                    binding.btnSimulateNextStep.text = "Next: Preparing"
                }
                2 -> {
                    binding.tvLiveETA.text = "18 - 22 mins"
                    binding.btnSimulateNextStep.text = "Next: On The Way"
                }
                3 -> {
                    binding.tvLiveETA.text = "5 - 8 mins"
                    binding.btnSimulateNextStep.text = "Next: Delivered"
                }
                4 -> {
                    binding.tvLiveETA.text = "Delivered ✓"
                    binding.btnSimulateNextStep.text = "Completed ✓"
                }
            }
        }
    }
}
