package com.example.pokedex.viewModel

import androidx.lifecycle.*
import com.example.pokedex.Repository
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonEntry

private const val TAG = "Pokemon Show View Model"

class PokemonShowViewModel(val pokemonEntry: PokemonEntry) : ViewModel() {
    val pokemon: LiveData<Pokemon> = Repository.getPokemon(pokemonEntry)

    fun cancelJob() {
        Repository.cancelJobs()
    }
}

