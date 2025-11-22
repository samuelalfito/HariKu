package com.hariku.feature_home.di

import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_home.data.local.MoodDao
import com.hariku.feature_home.data.local.MoodDatabase
import com.hariku.feature_home.data.remote.MoodFirebaseService
import com.hariku.feature_home.data.repository.MoodRepositoryImpl
import com.hariku.feature_home.domain.repository.MoodRepository
import com.hariku.feature_home.domain.usecase.GetTodayMoodUseCase
import com.hariku.feature_home.domain.usecase.SaveMoodUseCase
import com.hariku.feature_home.presentation.MoodViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    
    // Database
    single<MoodDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MoodDatabase::class.java,
            "mood_database"
        ).build()
    }
    
    // DAO
    single<MoodDao> { get<MoodDatabase>().moodDao() }
    
    // Firebase
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    
    // Remote Service
    single<MoodFirebaseService> { MoodFirebaseService(get()) }
    
    // Repository
    single<MoodRepository> {
        MoodRepositoryImpl(
            moodDao = get(),
            firebaseService = get()
        )
    }
    
    // Use Cases
    factoryOf(::SaveMoodUseCase)
    factoryOf(::GetTodayMoodUseCase)
    
    // ViewModel
    viewModelOf(::MoodViewModel)
}


