package com.example.pokedex.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
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
private const val NAV_ARGS = "pokedexEntry"

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAdapter = PokedexEntryAdapter()
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

    private class PokedexEntryHolder(val binding: ListItemPokedexListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val bundle = bundleOf(Pair(NAV_ARGS, binding.pokemon))
            Log.d(TAG, "bundle: $bundle")
            binding.root.findNavController().navigate(R.id.action_pokedexListFragment_to_pokemonShowFragment, bundle)
        }

        fun bind(entry: PokedexEntry) {
            Glide.with(binding.root).load(entry.spriteUrl).placeholder(R.drawable.ic_pokeball).into(binding.imageView)
            binding.apply {
                pokemon = entry
                executePendingBindings()
            }
        }

    }

    private inner class PokedexEntryAdapter :
        PagedListAdapter<PokedexEntry, PokedexEntryHolder>(
            object : DiffUtil.ItemCallback<PokedexEntry>() {
                override fun areItemsTheSame(
                    oldItem: PokedexEntry,
                    newItem: PokedexEntry
                ): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: PokedexEntry,
                    newItem: PokedexEntry
                ): Boolean  =
                    oldItem == newItem
            }
        ) {
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

        override fun onBindViewHolder(holder: PokedexEntryHolder, position: Int) {
            val entry: PokedexEntry? = getItem(position)
            holder.bind(entry!!)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PokedexListFragment()
    }
}
