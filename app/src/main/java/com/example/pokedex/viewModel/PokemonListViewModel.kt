package com.example.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.model.PokemonEntries
import kotlinx.coroutines.Dispatchers

private const val TAG = "Pokemon List View Model"

class PokemonListViewModel : ViewModel() {
    val pokemon = LivePagedListBuilder<String, PokedexEntry>(
        object : DataSource.Factory<String, PokedexEntry>() {
            override fun create(): DataSource<String, PokedexEntry> {
                return PokemonEntries(viewModelScope)
            }
        },
        PagedList
            .Config
            .Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
    ).build()
}