package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val number_id: Int,
    val name: String,
    val sprites: PokemonSpriteUrls
)

data class PokemonSpriteUrls(
    @SerializedName("back_default") val backDefault: String,
    @SerializedName("front_default") val frontDefault: String
)