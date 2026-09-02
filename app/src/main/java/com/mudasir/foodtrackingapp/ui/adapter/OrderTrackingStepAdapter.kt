package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.StepState
import com.mudasir.foodtrackingapp.data.model.TrackingStep
import com.mudasir.foodtrackingapp.databinding.ItemTrackingStepBinding

class OrderTrackingStepAdapter(
    private var steps: List<TrackingStep> = emptyList(),
    private val onStepClick: ((TrackingStep, Int) -> Unit)? = null
) : RecyclerView.Adapter<OrderTrackingStepAdapter.StepViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<TrackingStep>) {
        steps = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemTrackingStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(steps[position], isLastItem = position == steps.size - 1, stepPosition = position + 1)
    }

    override fun getItemCount(): Int = steps.size

    inner class StepViewHolder(private val binding: ItemTrackingStepBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(step: TrackingStep, isLastItem: Boolean, stepPosition: Int) {
            val context = binding.root.context
            binding.tvStepTitle.text = step.title
            binding.tvStepDesc.text = step.description
            binding.tvStepTimestamp.text = step.timestamp

            // Hide connecting line for last item
            binding.viewTimelineLine.visibility = if (isLastItem) View.GONE else View.VISIBLE

            when (step.state) {
                StepState.COMPLETED -> {
                    binding.layoutStepIndicator.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.status_green))
                    binding.ivStepStatusIcon.setImageResource(R.drawable.ic_check_circle)
                    binding.ivStepStatusIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
                    binding.tvStepTitle.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
                    binding.viewTimelineLine.setBackgroundColor(ContextCompat.getColor(context, R.color.status_green))
                }
                StepState.IN_PROGRESS -> {
                    binding.layoutStepIndicator.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_primary))
                    binding.ivStepStatusIcon.setImageResource(R.drawable.ic_delivery_bike)
                    binding.ivStepStatusIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
                    binding.tvStepTitle.setTextColor(ContextCompat.getColor(context, R.color.purple_primary))
                    binding.viewTimelineLine.setBackgroundColor(ContextCompat.getColor(context, R.color.border_light))
                }
                StepState.PENDING -> {
                    binding.layoutStepIndicator.backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bg_chip))
                    binding.ivStepStatusIcon.setImageResource(R.drawable.ic_check_circle)
                    binding.ivStepStatusIcon.setColorFilter(ContextCompat.getColor(context, R.color.text_hint))
                    binding.tvStepTitle.setTextColor(ContextCompat.getColor(context, R.color.text_hint))
                    binding.viewTimelineLine.setBackgroundColor(ContextCompat.getColor(context, R.color.border_light))
                }
            }

            binding.root.setOnClickListener {
                onStepClick?.invoke(step, stepPosition)
            }
        }
    }
}
