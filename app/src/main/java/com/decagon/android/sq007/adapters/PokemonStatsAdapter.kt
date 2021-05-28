package com.decagon.android.sq007.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.StatCardViewBinding
import com.decagon.android.sq007.model.subModel.Stat

// Stat adapter
class PokemonStatsAdapter(var items: List<Stat>, private val context: Context) :
    RecyclerView.Adapter<PokemonStatsAdapter.CardViewHolder>() {

    inner class CardViewHolder constructor(val binding: StatCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: Stat) {
            // Attaching the text to the field
            binding.statName.text = stat.stat.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            StatCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount(): Int {
        return items.size
    }
}
