package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.PokedexEntry
import kotlinx.coroutines.Dispatchers

private const val TAG = "Pokemon Show View Model"

class PokemonShowViewModel(val pokedexEntry: PokedexEntry) : ViewModel() {
    private val url = pokedexEntry.url

    val pokemon = liveData(Dispatchers.IO) {
        val retrievedPokemon = PokemonApi.getPokemon(url).body()!!
        emit(retrievedPokemon)
    }
}

