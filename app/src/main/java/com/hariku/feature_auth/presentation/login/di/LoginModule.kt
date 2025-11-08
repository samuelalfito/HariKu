package com.hariku.feature_auth.presentation.login.di

import com.hariku.feature_auth.data.AuthRepositoryImpl
import com.hariku.feature_auth.domain.AuthRepository
import com.hariku.feature_auth.presentation.login.LoginScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get(),
            firestore = get()
        )
    }
    viewModel {
        LoginScreenViewModel(get())
    }
}