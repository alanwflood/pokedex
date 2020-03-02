package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.model.Pokemon
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.rx2.await

private const val TAG = "Pokemon Show View Model"

class PokemonShowViewModel(val pokedexEntry: PokedexEntry): ViewModel() {
    private val  apiRepository = PokemonApi()
    private val url = pokedexEntry.url

    val pokemon = liveData(Dispatchers.IO)  {
        val retrievedPokemon = apiRepository.getPokemon(url)
        Log.d(TAG, "retrievedPokemon: $retrievedPokemon")
        emit(retrievedPokemon)
    }

    val pokemonRx = apiRepository.getPokemon(url).

}