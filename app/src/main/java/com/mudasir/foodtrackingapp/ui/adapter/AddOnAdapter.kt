package com.mudasir.foodtrackingapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mudasir.foodtrackingapp.R
import com.mudasir.foodtrackingapp.data.model.AddOn
import com.mudasir.foodtrackingapp.databinding.ItemAddonBinding

class AddOnAdapter(
    private var addOns: List<AddOn> = emptyList(),
    private val onAddOnToggle: (AddOn) -> Unit
) : RecyclerView.Adapter<AddOnAdapter.AddOnViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<AddOn>) {
        addOns = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddOnViewHolder {
        val binding = ItemAddonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddOnViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddOnViewHolder, position: Int) {
        holder.bind(addOns[position])
    }

    override fun getItemCount(): Int = addOns.size

    inner class AddOnViewHolder(private val binding: ItemAddonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(addOn: AddOn) {
            binding.ivAddOnIcon.setImageResource(addOn.iconRes)
            val context = binding.root.context

            if (addOn.isSelected) {
                binding.cardAddOn.setBackgroundResource(R.drawable.bg_card_food)
                binding.badgeAddOnAction.setBackgroundResource(R.drawable.bg_badge_add_green)
                binding.ivBadgeIcon.setImageResource(R.drawable.ic_check_circle)
                binding.ivBadgeIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.cardAddOn.setBackgroundResource(R.drawable.bg_addon_item)
                binding.badgeAddOnAction.setBackgroundResource(R.drawable.bg_badge_add_green)
                binding.ivBadgeIcon.setImageResource(R.drawable.ic_add)
                binding.ivBadgeIcon.setColorFilter(ContextCompat.getColor(context, R.color.white))
            }

            binding.root.setOnClickListener {
                addOn.isSelected = !addOn.isSelected
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    notifyItemChanged(pos)
                }
                onAddOnToggle(addOn)
            }
        }
    }
}
