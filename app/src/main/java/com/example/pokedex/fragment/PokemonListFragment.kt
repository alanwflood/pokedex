package com.example.pokedex.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.adapters.PokedexListEntryAdapter
import com.example.pokedex.databinding.FragmentPokemonListBinding
import com.example.pokedex.viewModel.PokemonListViewModel


/**
 * Use the [PokedexListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokemonListFragment : Fragment() {
    private lateinit var binding: FragmentPokemonListBinding

    // Setup viewModel for Fragment
    private val pokedexListViewModel: PokemonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using DataBinding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pokemon_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAdapter = PokedexListEntryAdapter()

        pokedexListViewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemonEntry ->
                viewAdapter.submitList(pokemonEntry)
            }
        )

        binding.pokedexListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PokemonListFragment()
    }
}
