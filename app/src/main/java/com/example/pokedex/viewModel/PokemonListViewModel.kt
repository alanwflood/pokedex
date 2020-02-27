package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokedex.api.PokemonApi
import kotlinx.coroutines.Dispatchers

private const val TAG = "Pokemon List View Model"

class PokemonListViewModel: ViewModel() {
    var offset = MutableLiveData<Int>(0)
    private val  apiRepository = PokemonApi()

    val pokemonEntriesLiveData = liveData(Dispatchers.IO)  {
        val retrievedPokemonEntries = apiRepository.getPokemonList(0)
        emit(retrievedPokemonEntries)
    }
}