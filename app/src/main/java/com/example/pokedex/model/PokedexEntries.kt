package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class PokedexEntry(
    private val _name: String,
    val url: String
) {
    val name: String
        get() = _name.capitalize()

    // This is fairly brittle, but saves having to send heaps of requests,
    // Rather than continually firing request for each pokemon,
    // Just get it's ID from the returned URL, it's the last param in the path
    // EX: https://pokeapi.co/api/v2/pokemon/7/
    // IMO: pokeapi is pretty poor in this regard, it should just return an extra id field in the response ¯\_(ツ)_/¯
    val id: String
        get() = url.split("/").reversed()[1]
    // Parse a URL using the ID
    val spriteUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

data class PokedexEntries(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("previous") val previousUrl: String?,
    @SerializedName("results") val entries: List<PokedexEntry>
)
