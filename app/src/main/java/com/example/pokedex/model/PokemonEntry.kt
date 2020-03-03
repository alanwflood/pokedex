package com.example.pokedex.model

import android.net.Uri
import android.os.Parcelable
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pokedex.api.PokemonApi
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.util.*

// Hard Limit to meaningful data in pokeapi,
// pokemon after this are different forms/mega types which I don't want
private const val POKEAPI_LIMIT = 807

@Parcelize
data class PokemonEntry(
    @SerializedName("name") private val _name: String,
    val url: String
) : Parcelable {

    @ExperimentalStdlibApi
    val name: String
        get() = _name.capitalize(Locale.ROOT)

    /**
     *  This is fairly brittle, but saves having to send heaps of requests,
     * Rather than continually firing request for each pokemon,
     * Just get it's ID from the returned URL, it's the last param in the path
     * Ex: https://pokeapi.co/api/v2/pokemon/7/
     * IMO: pokeapi is pretty poor in this regard,
     * it should just return an extra id field in the response ¯\_(ツ)_/¯
     */
    val id: String
        get() = url.split("/").reversed()[1]
    // Parse a URL using the ID
    val spriteUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    val displayId: String
        get() = id.padStart(3, '0')
}

data class PokemonEntries(
    @SerializedName("next") val nextUrl: String?,
    @SerializedName("previous") val previousUrl: String?,
    @SerializedName("results") val _entries: List<PokemonEntry>
) {
    // Filter out the list to remove junk Pokemon data
    val entries: List<PokemonEntry>
        get() = _entries.filter {
            it.id.toInt() <= POKEAPI_LIMIT
        }

    val next: String?
        get() = nextUrl?.let { getOffsetParamFromUrl(it) }

    val previous: String?
        get() = previousUrl?.let { getOffsetParamFromUrl(it) }

    private fun getOffsetParamFromUrl(url: String): String {
        val uri = Uri.parse(url)
        return URLDecoder.decode(uri.getQueryParameter("offset"), "UTF-8")
    }
}

/**
 * Setup [PokemonEntries] and [PokemonEntry] for pagination
 */
class PagedPokemonEntries(private val scope: CoroutineScope) :
    PageKeyedDataSource<String, PokemonEntry>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PokemonEntry>
    ) {
        scope.launch {
            try {
                val response = PokemonApi.getPokemonList()
                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        callback.onResult(
                            data?.entries ?: listOf(),
                            data?.previous,
                            data?.next
                        )
                    }
                }
            } catch (exception: Exception) {
                Log.e("Pokemon Entries", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokemonEntry>
    ) {
        scope.launch {
            try {
                val response = PokemonApi.getPokemonList(
                    offset = params.key.toInt(),
                    limit = params.requestedLoadSize
                )
                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        callback.onResult(data?.entries ?: listOf(), data?.next)
                    }
                }
            } catch (exception: Exception) {
                Log.e("Pokemon Entries", "Failed to fetch next data!")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokemonEntry>
    ) {
        scope.launch {
            try {
                val response = PokemonApi.getPokemonList(
                    offset = params.key.toInt(),
                    limit = params.requestedLoadSize
                )
                when {
                    response.isSuccessful -> {
                        val data = response.body()
                        callback.onResult(data?.entries ?: listOf(), data?.previous)
                    }
                }
            } catch (exception: Exception) {
                Log.e("Pokemon Entries", "Failed to fetch previous data!")
            }
        }
    }

}
