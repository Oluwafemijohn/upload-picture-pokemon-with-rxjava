package com.decagon.android.sq007.api

import com.decagon.android.sq007.model.mainModel.PokemonModel
import com.decagon.android.sq007.model.subModel.PokemonSubModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    // Used to get pokemons with limit
    @GET("pokemon")
    fun retrofitPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<PokemonModel>

    // Used to get pokemons when url is passed from the activity
    @GET
    fun getPokemonDetails(@Url urlPosition: String): Call<PokemonSubModel>
}
