package com.example.pokedex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonShowBinding

class PokemonShowFragment: Fragment() {
    private lateinit var binding: FragmentPokemonShowBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pokemon_show,
            container,
            false
        )
        return binding.root
    }
}