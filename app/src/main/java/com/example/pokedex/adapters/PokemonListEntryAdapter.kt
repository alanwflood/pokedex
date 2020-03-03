package com.example.pokedex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ListItemPokedexListBinding
import com.example.pokedex.model.PokedexEntry

private const val NAV_ARGS = "pokedexEntry"

class PokedexListEntryAdapter :
    PagedListAdapter<PokedexEntry, RecyclerView.ViewHolder>(PokemonListEntryDiffCallback) {
    private lateinit var binding: ListItemPokedexListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexEntryHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_pokedex_list,
            parent,
            false
        )
        return PokedexEntryHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entry: PokedexEntry? = getItem(position)
        (holder as PokedexEntryHolder).bind(entry!!)
    }

    class PokedexEntryHolder(val binding: ListItemPokedexListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val bundle = bundleOf(Pair(NAV_ARGS, binding.pokemon))
            binding.root.findNavController()
                .navigate(R.id.action_pokedexListFragment_to_pokemonShowFragment, bundle)
        }

        fun bind(entry: PokedexEntry) {
            binding.apply {
                pokemon = entry
                executePendingBindings()
            }
        }
    }
}

private object PokemonListEntryDiffCallback : DiffUtil.ItemCallback<PokedexEntry>() {
    override fun areItemsTheSame(
        oldItem: PokedexEntry,
        newItem: PokedexEntry
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PokedexEntry,
        newItem: PokedexEntry
    ): Boolean =
        oldItem == newItem
}
