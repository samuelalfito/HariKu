package com.hariku.feature_auth.presentation.register.di

import com.hariku.feature_auth.data.AuthRepositoryImpl
import com.hariku.feature_auth.domain.AuthRepository
import com.hariku.feature_auth.presentation.register.RegisterScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get(),
            firestore = get()
        )
    }
    viewModel {
        RegisterScreenViewModel(get())
    }
}