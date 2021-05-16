package com.decagon.android.sq007.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.decagon.android.sq007.R
import com.decagon.android.sq007.api.PokemonApi
import com.decagon.android.sq007.api.PokemonRetrofit
import com.decagon.android.sq007.controller.OnItemClickListener
import com.decagon.android.sq007.controller.PokemonAdapter
import com.decagon.android.sq007.databinding.ActivityMainBinding
import com.decagon.android.sq007.implementationTwo.ImplemeantationTwoActivity
import com.decagon.android.sq007.model.mainModel.PokemonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var pokeApi: PokemonApi
    lateinit var adapter: PokemonAdapter

    // The retrofit callback handler method
    private val callback = object : Callback<PokemonModel> {
        // The on failure
        override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
            Log.e("MainActivity", "Problem calling API {${t?.message}}")
        }
        // On success handler method
        override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
            response?.isSuccessful.let {
                // attaching the response to the adapter
                val resultList = response.body()
                adapter = resultList?.let { it1 -> PokemonAdapter(it1, this@MainActivity, this@MainActivity) }!!
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokeApi = PokemonRetrofit.getPokemonEndpoint()
        pokeApi.retrofitPokemon(100, 0).enqueue(callback)

        // Select the number of pokemon to display
        binding.sendLimit.setOnClickListener {
            try {
                // validation of the input field
                if (TextUtils.isEmpty(binding.numberOfPokemon.text)) {
                    binding.numberOfPokemon.error = "Enter a number"
                    return@setOnClickListener
                } else {
                    // calling the retrofit function
                    var number = binding.numberOfPokemon.text.toString().toInt()
                    pokeApi.retrofitPokemon(number, 0).enqueue(callback)
                    binding.numberOfPokemon.text.clear()
                    it.hideKeyboard()
                }
            } catch (e: ArithmeticException) {
                println(e)
            }
        }

        // Attaching the linearlayout manager
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        supportActionBar?.hide()

        // Adding the action bar to it tool bar
        setSupportActionBar(findViewById(R.id.contact_one_tool_bar))
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    // Onclick listener and sending the name to the second activity
    override fun onItemClick(position: Int, items: PokemonModel) {
        var pokemonName = items.results[position].name
        var pokemonUrl = items.results[position].url
        var position = position

        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra("Name", pokemonName)
        intent.putExtra("Url", pokemonUrl)
        intent.putExtra("Position", (position + 1).toString())
        Log.d("MainActivity_positio", "MAinActivity $position")
        startActivity(intent)
    }

    // To inflate the menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.implementation_two -> {
                var intent = Intent(this, ImplemeantationTwoActivity::class.java)
                startActivity(intent)

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
