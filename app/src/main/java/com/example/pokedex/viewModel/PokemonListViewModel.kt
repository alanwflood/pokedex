package com.example.pokedex.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokedex.model.PagedPokemonEntries
import com.example.pokedex.model.PokemonEntry

class PokemonListViewModel : ViewModel() {
    val pokemon = LivePagedListBuilder<String, PokemonEntry>(
        object : DataSource.Factory<String, PokemonEntry>() {
            override fun create(): DataSource<String, PokemonEntry> {
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