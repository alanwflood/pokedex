package com.example.pokedex.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokedexListBinding
import com.example.pokedex.databinding.ListItemPokedexListBinding
import com.example.pokedex.model.PokedexEntries
import com.example.pokedex.model.PokedexEntry
import com.example.pokedex.viewModel.PokemonListViewModel


private const val TAG = "Pokedex List Fragment"

/**
 * Use the [PokedexListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokedexListFragment : Fragment() {
    private lateinit var binding: FragmentPokedexListBinding

    // Setup viewModel for Fragment
    private val pokedexListViewModel: PokemonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using DataBinding
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pokedex_list,
            container,
            false
        )

        binding.pokedexListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokedexListViewModel.pokemonEntriesLiveData.observe(
            viewLifecycleOwner,
            Observer { pokedexEntries ->
                Log.d(TAG, "Have ${pokedexEntries.entries.size} pokemon in viewModel")
                binding.pokedexListRecyclerView.adapter = PokedexEntryAdapter(pokedexEntries = pokedexEntries)
            }
        )
    }

    private class PokedexEntryHolder(val binding: ListItemPokedexListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            binding.root.findNavController().navigate(R.id.action_pokedexListFragment_to_pokemonShowFragment)
        }

        fun bind(entry: PokedexEntry) {

            Log.d(TAG, "name: ${entry.name}")
            Log.d(TAG, "id: ${entry.id}")
            Log.d(TAG, "sprite: ${entry.spriteUrl}")
            Glide.with(binding.root).load(entry.spriteUrl).placeholder(R.drawable.ic_pokeball).into(binding.imageView)

            binding.apply {
                pokemon = entry

                executePendingBindings()
            }
        }

    }

    private inner class PokedexEntryAdapter(private val pokedexEntries: PokedexEntries) :
        RecyclerView.Adapter<PokedexEntryHolder>() {
        private lateinit var binding: ListItemPokedexListBinding
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexEntryHolder {
            binding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.list_item_pokedex_list,
                parent,
                false
            )
            return PokedexEntryHolder(binding)
        }

        override fun getItemCount(): Int = pokedexEntries.entries.size

        override fun onBindViewHolder(holder: PokedexEntryHolder, position: Int) {

            holder.bind(pokedexEntries.entries[position])
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PokedexListFragment()
    }
}
