package com.hariku.feature_auth.di

import com.hariku.feature_auth.data.remote.AuthRemoteDataSource
import com.hariku.feature_auth.data.repository.AuthRepositoryImpl
import com.hariku.feature_auth.domain.repository.AuthRepository
import com.hariku.feature_auth.domain.usecase.GetCurrentUserUseCase
import com.hariku.feature_auth.domain.usecase.LoginUseCase
import com.hariku.feature_auth.domain.usecase.SignUpUseCase
import com.hariku.feature_auth.presentation.login.LoginScreenViewModel
import com.hariku.feature_auth.presentation.register.RegisterScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Module Koin untuk feature_auth.
 * Mengikuti Clean Architecture: Presentation → Domain (UseCase) → Data (Repository) → Remote/Local
 */
val authModule = module {
    
    // ============================================
    // DATA LAYER - Remote Data Source
    // ============================================
    single {
        AuthRemoteDataSource(
            auth = get(),
            firestore = get()
        )
    }
    
    // ============================================
    // DATA LAYER - Repository Implementation
    // ============================================
    single<AuthRepository> {
        AuthRepositoryImpl(
            remoteDataSource = get()
        )
    }
    
    // ============================================
    // DOMAIN LAYER - Use Cases
    // ============================================
    factory {
        LoginUseCase(repository = get())
    }
    
    factory {
        SignUpUseCase(repository = get())
    }
    
    factory {
        GetCurrentUserUseCase(repository = get())
    }
    
    // ============================================
    // PRESENTATION LAYER - ViewModels
    // ============================================
    viewModel {
        LoginScreenViewModel(get())
    }
    
    viewModel {
        RegisterScreenViewModel(get())
    }
}

