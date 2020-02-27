package com.example.pokedex.api

import com.example.pokedex.model.PokedexEntries
import com.example.pokedex.model.Pokemon
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

class PokemonApi {
    private val BASE_URL = HttpUrl
        .Builder()
        .scheme("https")
        .host( "pokeapi.co")
        .addPathSegments("api/v2/")

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL.toString())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        ).build()
    private var client: PokemonApiCalls = retrofit.create(PokemonApiCalls::class.java)

    suspend fun getPokemonList(offset: Int = 0) = client.fetchPokemonList(offset = offset)
    suspend fun getPokemon(url: String) = client.fetchPokemon(url)
}

interface PokemonApiCalls {
    @GET("pokemon/")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 151
    ): PokedexEntries

    @GET
    suspend fun fetchPokemon(
        @Url url: String
    ): Pokemon
}