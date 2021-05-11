package com.decagon.android.sq007.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.decagon.android.sq007.api.PokemonApi
import com.decagon.android.sq007.api.PokemonRetrofit
import com.decagon.android.sq007.controller.PokemonAbilityAdapter
import com.decagon.android.sq007.controller.PokemonMoveAdapter
// import com.decagon.android.sq007.controller.PokemonSpritesAdapter
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
//    lateinit var gameIndicesAdapter: PokemonSpritesAdapter

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
            override fun onFailure(call: Call<PokemonSubModel>, t: Throwable) {
                Log.e("MainActivity", "Problem calling API {${t?.message}}")
            }

            override fun onResponse(call: Call<PokemonSubModel>, response: Response<PokemonSubModel>) {
                response?.isSuccessful.let {
                    var characterList = response.body()
                    if (characterList != null) {
                        abilityAdapter = PokemonAbilityAdapter(characterList.abilities, this@PokemonDetailsActivity)!!
                        MoveAdapter = PokemonMoveAdapter(characterList.moves)!!
//                        gameIndicesAdapter = PokemonSpritesAdapter(characterList.sprites)!!
                        binding.detailAbilityRecyclerView.adapter = abilityAdapter
                        binding.detailFormRecyclerView.adapter = MoveAdapter
                        binding.gameIndices.text = characterList.game_indices[0].game_index.toString()
                    }
                }
            }
        }

        // Calling the Get method for the API and Appending the API endpoint to it
        if (url != null) {
            pokeApi.getPokemonDetails(url).enqueue(callback)
        }

        binding.pokemonDetailName.text = intent.getStringExtra("Name")
        binding.detailAbilityRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.detailFormRecyclerView.layoutManager = GridLayoutManager(this, 2)

        var position = intent.extras?.getString("Position")

        Log.d("IMAGW_POSITION", "onItemClick: $position")
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$position.png")
            .into(binding.pokemonDetailImage)
//        binding.pokemonDetailImage
    }
}
