package com.decagon.android.sq007.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.decagon.android.sq007.api.PokemonApi
import com.decagon.android.sq007.api.PokemonRetrofit
import com.decagon.android.sq007.controller.PokemonAbilityAdapter
import com.decagon.android.sq007.controller.PokemonMoveAdapter
import com.decagon.android.sq007.controller.PokemonStatsAdapter
import com.decagon.android.sq007.databinding.ActivityPokemonDetailsBinding
import com.decagon.android.sq007.model.subModel.PokemonSubModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var pokeApi: PokemonApi

    lateinit var abilityAdapter: PokemonAbilityAdapter
    lateinit var MoveAdapter: PokemonMoveAdapter
    lateinit var pokemonStatsAdapter: PokemonStatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Hide action bar
        supportActionBar?.hide()

        // Getting URL from the intent
        var url = intent.getStringExtra("Url")

        // Calling the Retrofit function
        pokeApi = PokemonRetrofit.getPokemonEndpoint()

        // Response handling
        val callback = object : Callback<PokemonSubModel> {
            // On Failure handler
            override fun onFailure(call: Call<PokemonSubModel>, t: Throwable) {
            }
            // On success handler
            override fun onResponse(call: Call<PokemonSubModel>, response: Response<PokemonSubModel>) {
                response?.isSuccessful.let {
                    // attachin the response to the adapter
                    var characterList = response.body()
                    if (characterList != null) {
                        abilityAdapter = PokemonAbilityAdapter(characterList.abilities, this@PokemonDetailsActivity)!!
                        pokemonStatsAdapter = PokemonStatsAdapter(characterList.stats, this@PokemonDetailsActivity)!!
                        MoveAdapter = PokemonMoveAdapter(characterList.moves)!!
                        binding.detailStatsRecyclerView.adapter = pokemonStatsAdapter
                        binding.detailAbilityRecyclerView.adapter = abilityAdapter
                        binding.detailMoveRecyclerView.adapter = MoveAdapter
                        binding.gameIndices.text = characterList.game_indices[0].game_index.toString()

                        // Attaching the back default image
                        var backDefault = characterList.sprites.back_default
                        if (backDefault == null) binding.backDefaultLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(backDefault)
                            .into(binding.backDefault)

                        // Attaching the back female Image
                        var backFemale = characterList.sprites.back_female
                        if (backFemale == null) binding.backFemaleLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(backFemale)
                            .into(binding.backFemale)

                        // Attaching the back shiny
                        var backShiny = characterList.sprites.back_shiny
                        if (backShiny == null) binding.backFemaleLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(backShiny)
                            .into(binding.backShiny)

                        // Attaching the back shiny female
                        var backShinyFemale = characterList.sprites.back_shiny_female
                        if (backShinyFemale == null) binding.backShinyFemaleLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(backShinyFemale)
                            .into(binding.backShinyFemale)

                        // Attaching the back front female
                        var frontFemale = characterList.sprites.front_female
                        if (frontFemale == null) binding.frontFemaleLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(frontFemale)
                            .into(binding.frontFemale)

                        // Attaching the front shiny
                        var frontShiny = characterList.sprites.front_shiny
                        Log.d("frontShiny", "onResponse: $frontShiny")
                        if (frontShiny == null) binding.frontShinyAyer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(frontShiny)
                            .into(binding.frontShiny)
                        // Attaching front shiny female
                        var frontShinyFemale = characterList.sprites.front_shiny_female
                        if (frontShinyFemale == null) binding.frontShinyFemaleLayer.visibility = View.GONE
                        Glide.with(this@PokemonDetailsActivity)
                            .load(frontShinyFemale)
                            .into(binding.frontShinyFemale)
                    }
                }
            }
        }

        // Calling the Get method for the API and Appending the API endpoint to it
        if (url != null) {
            pokeApi.getPokemonDetails(url).enqueue(callback)
        }

        // puting grid layout manager
        binding.pokemonDetailName.text = intent.getStringExtra("Name")
        binding.detailAbilityRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailMoveRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailStatsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Attaching the first image at the details page
        var position = intent.extras?.getString("Position")
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$position.png")
            .into(binding.pokemonDetailImage)
    }
}
