package com.decagon.android.sq007.view

import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.decagon.android.sq007.R
import com.decagon.android.sq007.adapters.OnItemClickListener
import com.decagon.android.sq007.adapters.PokemonAdapter
import com.decagon.android.sq007.api.PokemonApi
import com.decagon.android.sq007.api.PokemonRetrofit
import com.decagon.android.sq007.databinding.ActivityMainBinding
import com.decagon.android.sq007.implementationTwo.ImplementationTwoActivity
import com.decagon.android.sq007.model.mainModel.PokemonModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var pokeApi: PokemonApi
    lateinit var disposable: Disposable

    val adapter = PokemonAdapter(this@MainActivity, this@MainActivity)

    // Fetching the data with RxJava
    private fun fetchData(limit: Int) {
        CompositeDisposable().add(
            // Adding the data from the pokemon API
            pokeApi.retrofitPokemon(limit, 0)
                // subscribing on the background thread
                .subscribeOn(Schedulers.io())
                // Observing it on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                // Working with the data
                .subscribe { pokemonDex ->
                    adapter.setData(pokemonDex)
                    binding.recyclerView.adapter = adapter
                }
        )
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokeApi = PokemonRetrofit.getPokemonEndpoint()

        checkNetworkState(100)

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
                    checkNetworkState(number)
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

    // Checkjing network connection to preent crashes when there is network
    fun checkNetworkState(number: Int) {
        disposable = ReactiveNetwork
            .observeNetworkConnectivity(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.state() == NetworkInfo.State.CONNECTED) {
                    fetchData(number)
                } else if (it.state() == NetworkInfo.State.DISCONNECTED) {
                    Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
                var intent = Intent(this, ImplementationTwoActivity::class.java)
                startActivity(intent)

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
