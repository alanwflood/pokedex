package com.example.pokedex.model

import com.example.pokedex.model.common.NameResponse
import com.google.gson.annotations.SerializedName
import java.util.*

data class Pokemon(
    val data: PokemonData?,
    val species: PokemonSpecies?
)

data class PokemonData(
    @SerializedName("name") val _name: String,
    val sprites: PokemonSpriteUrls,
    var species: PokemonSpecies? = null
) {
    @ExperimentalStdlibApi
    val name: String
        get() = _name.capitalize(Locale.ROOT)
}

data class PokemonSpriteUrls(
    @SerializedName("back_default") val backDefault: String,
    @SerializedName("front_default") val frontDefault: String
)


data class PokemonSpecies(
    @SerializedName("color") val color: String,
    @SerializedName("flavour_text_entries") val flavourTextEntries: List<FlavourTextEntry>
)

data class FlavourTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    private val _language: NameResponse,
    private val _version: NameResponse
) {
    val language: String
        get() = _language.name
    val gameVersion: String
        get() = _version.name
}
