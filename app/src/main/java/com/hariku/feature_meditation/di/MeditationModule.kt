package com.hariku.feature_meditation.di

import androidx.room.Room
import com.hariku.feature_meditation.data.local.MeditationDatabase
import com.hariku.feature_meditation.data.local.MeditationSongDao
import com.hariku.feature_meditation.data.remote.MeditationRemoteDataSource
import com.hariku.feature_meditation.data.repository.MeditationRepositoryImpl
import com.hariku.feature_meditation.domain.repository.MeditationRepository
import com.hariku.feature_meditation.domain.usecase.GetAllMeditationSongsUseCase
import com.hariku.feature_meditation.domain.usecase.GetMeditationSongByIdUseCase
import com.hariku.feature_meditation.presentation.MeditationSongViewModel
import com.hariku.feature_meditation.presentation.MeditationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val meditationModule = module {
    
    single {
        Room.databaseBuilder(
            androidContext(),
            MeditationDatabase::class.java,
            "meditation_database"
        ).build()
    }
    
    single<MeditationSongDao> {
        get<MeditationDatabase>().meditationSongDao()
    }
    
    single {
        MeditationRemoteDataSource()
    }
    
    single<MeditationRepository> {
        MeditationRepositoryImpl(
            remoteDataSource = get(),
            dao = get()
        )
    }
    
    factory {
        GetAllMeditationSongsUseCase(get())
    }
    
    factory {
        GetMeditationSongByIdUseCase(get())
    }
    
    viewModel {
        MeditationViewModel(get())
    }
    
    viewModel {
        MeditationSongViewModel(get())
    }
}

