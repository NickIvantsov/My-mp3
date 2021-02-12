package com.gmail.ivantsov.nikolai.my_mp3.di

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.ivantsov.nikolai.my_mp3.framework.IInitSongsFilterComponent
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.SongsAdapter
import com.gmail.ivantsov.nikolai.my_mp3.presentation.library.SongsFilter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val adapterModule = module {
    fun provideSongsAdapter(
        songsFilter: SongsFilter,
        initSongsFilterComponent: IInitSongsFilterComponent
    ): SongsAdapter {
        return SongsAdapter(songsFilter, initSongsFilterComponent)
    }

    fun provideSongsFilter(): SongsFilter {
        return SongsFilter()
    }

    fun provideDividerItemDecoration(context: Context, decoration: Int): DividerItemDecoration {
        return DividerItemDecoration(context, decoration)
    }

    factory {
        provideSongsAdapter(get(), get<SongsFilter>())
    }
    single {
        provideSongsFilter()
    }
    single {
        provideDividerItemDecoration(androidContext(), DividerItemDecoration.VERTICAL)
    }
}