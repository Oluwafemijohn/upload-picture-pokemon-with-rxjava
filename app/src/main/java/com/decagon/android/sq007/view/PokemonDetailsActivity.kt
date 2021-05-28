package com.decagon.android.sq007.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.decagon.android.sq007.adapters.PokemonAbilityAdapter
import com.decagon.android.sq007.adapters.PokemonMoveAdapter
import com.decagon.android.sq007.adapters.PokemonStatsAdapter
import com.decagon.android.sq007.api.PokemonApi
import com.decagon.android.sq007.api.PokemonRetrofit
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
            override fun onResponse(
                call: Call<PokemonSubModel>,
                response: Response<PokemonSubModel>
            ) {
                response?.isSuccessful.let {
                    // attachin the response to the adapter
                    var characterList = response.body()
                    if (characterList != null) {
                        abilityAdapter = PokemonAbilityAdapter(
                            characterList.abilities,
                            this@PokemonDetailsActivity
                        )!!
                        pokemonStatsAdapter =
                            PokemonStatsAdapter(characterList.stats, this@PokemonDetailsActivity)!!
                        MoveAdapter = PokemonMoveAdapter(characterList.moves)!!
                        binding.detailStatsRecyclerView.adapter = pokemonStatsAdapter
                        binding.detailAbilityRecyclerView.adapter = abilityAdapter
                        binding.detailMoveRecyclerView.adapter = MoveAdapter
                        binding.gameIndices.text =
                            characterList.game_indices[0].game_index.toString()

                        // Attaching the back default image
                        var backDefault = characterList.sprites.back_default
                        attachPicture(backDefault, binding.backDefault, binding.backDefaultLayer)

                        // Attaching the back female Image
                        var backFemale = characterList.sprites.back_female
                        attachPicture(backFemale, binding.backFemale, binding.backFemaleLayer)

                        // Attaching the back shiny
                        var backShiny = characterList.sprites.back_shiny
                        attachPicture(backShiny, binding.backShiny, binding.backFemaleLayer)

                        // Attaching the back shiny female
                        var backShinyFemale = characterList.sprites.back_shiny_female
                        attachPicture(backShinyFemale, binding.backShinyFemale, binding.backShinyFemaleLayer)

                        // Attaching the back front female
                        var frontFemale = characterList.sprites.front_female
                        attachPicture(frontFemale, binding.frontFemale, binding.frontFemaleLayer)

                        // Attaching the front shiny
                        var frontShiny = characterList.sprites.front_shiny
                        attachPicture(frontShiny, binding.frontShiny, binding.frontShinyAyer)

                        // Attaching front shiny female
                        var frontShinyFemale = characterList.sprites.front_shiny_female
                        attachPicture(frontShinyFemale, binding.frontShinyFemale, binding.frontShinyFemaleLayer)
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

    fun View.hide() {
        this.visibility = View.GONE
    }

    // Attaching pokemon images to the Image view
    fun attachPicture(spriteList: Any?, imageView: ImageView, imageLinearLayer: LinearLayout) {
        if (spriteList == null) imageLinearLayer.hide()
        Glide.with(this@PokemonDetailsActivity)
            .load(spriteList)
            .into(imageView)
    }
}
