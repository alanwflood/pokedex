package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.model.PokemonEntry

/**
 * Factory for creating a [PokemonShowViewModel] with a constructor that takes a [PokemonEntry]
 */
class PokemonShowViewModelFactory(
    private val pokedexEntry: PokemonEntry
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d("Show View Model Factory", "$pokedexEntry")
        return PokemonShowViewModel(pokedexEntry) as T
    }
}