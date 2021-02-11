package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.ivantsov.nikolai.core.domain.Song
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.databinding.MainFragmentBinding
import org.koin.android.ext.android.inject


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var bindingImpl: MainFragmentBinding? = null
    private val binding get() = bindingImpl!!

    private val viewModel by inject<MainViewModel>()
    private val songsAdapter by inject<SongsAdapter>()
    private val dividerItemDecoration by inject<DividerItemDecoration>()
    private val linearLayoutManager by inject<LinearLayoutManager>()

    private val songsObserver = Observer<List<Song>> { songsList ->
        songsAdapter.addAll(songsList)
    }
    private val errorMsgObserver = Observer<Int> { errorMsg ->
        Toast.makeText(context, requireContext().resources.getText(errorMsg), Toast.LENGTH_LONG)
            .show()
        songsAdapter.isNeedPlay = false
    }
    private val itemSongListener = { song: Song ->
        viewModel.playSong(song)
    }
    private val searchTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            songsAdapter.filter.filter(newText)
            return false
        }
    }

    private val onMenuItemClickListener = { menuItem: MenuItem ->
        when (menuItem.itemId) {
            R.id.action_search -> {
                val searchManager =
                    requireActivity().getSystemService(Context.SEARCH_SERVICE) as? SearchManager
                searchManager?.let { searchManagerValue ->
                    (menuItem.actionView as? SearchView)?.let { searchViewValue ->
                        searchViewValue.apply {
                            setSearchableInfo(
                                searchManagerValue.getSearchableInfo(
                                    requireActivity().componentName
                                )
                            )
                            setOnQueryTextListener(searchTextListener)
                        }
                    }

                }
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
        viewModel.loadSongs()
        viewModel.getSongsLiveData().observe(viewLifecycleOwner, songsObserver)
        viewModel.getErrorLiveData().observe(viewLifecycleOwner, errorMsgObserver)
        songsAdapter.itemClickListener = itemSongListener
        binding.toolbar.setOnMenuItemClickListener(onMenuItemClickListener)
    }
}