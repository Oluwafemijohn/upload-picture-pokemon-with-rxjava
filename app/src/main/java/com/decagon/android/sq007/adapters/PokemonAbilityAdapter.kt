package com.decagon.android.sq007.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.AbilityCardViewBinding
import com.decagon.android.sq007.model.subModel.Ability

class PokemonAbilityAdapter(var items: List<Ability>, val context: Context) :
    RecyclerView.Adapter<PokemonAbilityAdapter.CardViewHolder>() {
    // Adapter class
    class CardViewHolder constructor(val binding: AbilityCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ability) {
            // binding the adapter with the data
            binding.abilityName.text = item.ability.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        val binding =
            AbilityCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
