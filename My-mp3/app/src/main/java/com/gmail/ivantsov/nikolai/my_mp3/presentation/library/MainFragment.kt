package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.databinding.MainFragmentBinding
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var bindingImpl: MainFragmentBinding? = null
    private val binding get() = bindingImpl!!

    private val viewModel by inject<MainViewModel>()
    private val songsAdapter by inject<SongsAdapter>()

    private val searchTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            songsAdapter.filter.filter(newText)
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingImpl = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSongs.apply {
            setHasFixedSize(true)
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainFragment.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        viewModel.loadSongs()
        viewModel.getSongsLiveData().observe(viewLifecycleOwner) { songsList ->
            songsAdapter.addAll(songsList)
        }
        viewModel.getErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
        songsAdapter.itemClickListener = {
            viewModel.playSong(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_search_song, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        searchItem.isEnabled = true
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        if (searchManager != null) {
            val searchView = searchItem.actionView as? SearchView
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                searchView.setOnQueryTextListener(searchTextListener)
            } else {
                Timber.d("searchView IS NULL")
            }
        } else {
            Timber.d("searchManager IS NULL")
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}