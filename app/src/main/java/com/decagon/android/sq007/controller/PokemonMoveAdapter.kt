package com.decagon.android.sq007.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.databinding.MoveCardViewBinding
import com.decagon.android.sq007.model.subModel.Move

// adapter for
class PokemonMoveAdapter(var items: List<Move>) :
    RecyclerView.Adapter<PokemonMoveAdapter.CardViewHolder>() {

    class CardViewHolder constructor(val binding: MoveCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(move: Move) {
            // attaching the value to text field
            binding.moveName.text = move.move.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            MoveCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(items[position])
    }
    override fun getItemCount(): Int {
        return items.size
    }
}
