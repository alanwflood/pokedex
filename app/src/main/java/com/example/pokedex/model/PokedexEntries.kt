package com.example.pokedex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PokedexEntry(
    @SerializedName("name") private val _name: String,
    val url: String
): Parcelable {
    @ExperimentalStdlibApi
    val name: String
        get() = _name.capitalize(Locale.getDefault())

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

    val displayId: String
        get() = id.padStart(3, '0')
}

data class PokedexEntries(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("previous") val previousUrl: String?,
    @SerializedName("results") val entries: List<PokedexEntry>
)
