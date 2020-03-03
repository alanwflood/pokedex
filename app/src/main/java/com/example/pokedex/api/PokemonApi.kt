package com.example.pokedex.api

import com.example.pokedex.model.PokemonEntries
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonData
import com.example.pokedex.model.PokemonSpecies
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

object PokemonApi {
    private val baseUrl = HttpUrl
        .Builder()
        .scheme("https")
        .host("pokeapi.co")
        .addPathSegments("api/v2/")

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl.toString())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            .build()
    }

    private var client: PokemonApiCalls = retrofit.create(PokemonApiCalls::class.java)

    suspend fun getPokemonList(
        offset: Int = 0,
        limit: Int = 20
    ) = client.fetchPokemonList(offset = offset, limit = limit)
    suspend fun getPokemon(id: String) = client.fetchPokemon(id)
    suspend fun getSpecies(id: String) = client.fetchPokemonSpecies(id)
}

interface PokemonApiCalls {
    @GET("pokemon/")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonEntries>

    @GET("pokemon/{id}")
    suspend fun fetchPokemon(
        @Path("id") id: String
    ): Response<PokemonData>

    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpecies(
        @Path("id") id: String
    ): Response<PokemonSpecies>
}