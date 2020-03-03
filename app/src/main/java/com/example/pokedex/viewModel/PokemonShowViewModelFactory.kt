package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.model.PokedexEntry

/**
 * Factory for creating a [PokemonShowViewModel] with a constructor that takes a [PokedexEntry]
 */
class PokemonShowViewModelFactory(
    private val pokedexEntry: PokedexEntry
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d("Show View Model Factory", "$pokedexEntry")
        return PokemonShowViewModel(pokedexEntry) as T
    }
}