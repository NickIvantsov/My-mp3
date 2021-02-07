package com.gmail.ivantsov.nikolai.my_mp3.presentation.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
            addItemDecoration(DividerItemDecoration(this@MainFragment.context,DividerItemDecoration.VERTICAL))
        }
        viewModel.loadSongs()
        viewModel.getSongsLiveData().observe(viewLifecycleOwner) { songsList ->
            songsAdapter.addAll(songsList)
        }
        songsAdapter.itemClickListener = {
            viewModel.playSong(it)
        }
    }
}