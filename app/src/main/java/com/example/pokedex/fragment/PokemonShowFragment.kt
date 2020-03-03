package com.example.pokedex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonShowBinding
import com.example.pokedex.viewModel.PokemonShowViewModel
import com.example.pokedex.viewModel.PokemonShowViewModelFactory

class PokemonShowFragment : Fragment() {
    private val args: PokemonShowFragmentArgs by navArgs()
    private val pokemonShowViewModel: PokemonShowViewModel by viewModels {
        PokemonShowViewModelFactory(args.pokedexEntry)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPokemonShowBinding>(
            inflater,
            R.layout.fragment_pokemon_show,
            container,
            false
        ).apply {
            viewModel = pokemonShowViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}