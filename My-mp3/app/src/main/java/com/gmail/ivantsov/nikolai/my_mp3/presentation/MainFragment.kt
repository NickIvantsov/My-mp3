package com.gmail.ivantsov.nikolai.my_mp3.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmail.ivantsov.nikolai.my_mp3.R
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.MainViewModel
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by inject<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadSongs()
        viewModel.getSongsLiveData().observe(viewLifecycleOwner){
            Log.d("TAG_LOG",it.toString())
        }
    }
}