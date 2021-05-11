package com.decagon.android.sq007.api

import com.decagon.android.sq007.model.mainModel.PokemonModel
import com.decagon.android.sq007.model.subModel.PokemonSubModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET("pokemon?limit=1118&offset=0")
    fun retrofitPokemon(): Call<PokemonModel>

    @GET
    fun getPokemonDetails(@Url urlPosition: String): Call<PokemonSubModel>
}
