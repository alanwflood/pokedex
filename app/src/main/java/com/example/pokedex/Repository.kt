package com.example.pokedex

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonEntry
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Repository {
    var job: CompletableJob? = null

    fun getPokemon(pokemonEntry: PokemonEntry): LiveData<Pokemon> {
        return object : LiveData<Pokemon>() {
            override fun onActive() {
                super.onActive()
                job?.let { fetchJob ->
                    CoroutineScope(IO + fetchJob).launch {
                        try {
                            val pokemonResponse = PokemonApi.getPokemon(pokemonEntry.id)
                            val speciesResponse = PokemonApi.getSpecies(pokemonEntry.id)
                            when {
                                pokemonResponse.isSuccessful && speciesResponse.isSuccessful -> {
                                    withContext(Main) {
                                        value = Pokemon(pokemonResponse.body(), speciesResponse.body())
                                    }
                                }
                            }
                        } catch (exception: Exception) {
                            Log.e(
                                "Repository",
                                "Failed to fetch data for id: ${pokemonEntry.id}!"
                            )
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }
}

