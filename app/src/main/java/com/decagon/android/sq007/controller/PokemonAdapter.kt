package com.decagon.android.sq007.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.android.sq007.databinding.CardviewBinding
import com.decagon.android.sq007.model.mainModel.PokemonModel
import com.decagon.android.sq007.model.mainModel.Result

// Adapter for the list of pokemons
class PokemonAdapter(
    var items: PokemonModel,
    private val listener: OnItemClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<PokemonAdapter.CardViewHolder>() {

    inner class CardViewHolder constructor(val binding: CardviewBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        fun bind(result: Result) {
            // pokemont name totext field
            binding.pokemonTitle.text = result.name
            // Attaching the image to the recyclerview
            Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${adapterPosition + 1}.png")
                .into(binding.recyclerViewPokemonImage)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(items.results[position])
    }

    override fun getItemCount(): Int {
        return items.results.size
    }
}

// Onclick interface
interface OnItemClickListener {
    fun onItemClick(position: Int, items: PokemonModel)
}
