package com.example.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.model.PagedPokemonEntries

class PokemonListViewModel : ViewModel() {
    val pokemon = LivePagedListBuilder<String, PokedexEntry>(
        object : DataSource.Factory<String, PokedexEntry>() {
            override fun create(): DataSource<String, PokedexEntry> {
                return PagedPokemonEntries(viewModelScope)
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