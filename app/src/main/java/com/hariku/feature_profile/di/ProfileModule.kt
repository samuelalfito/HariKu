package com.hariku.feature_profile.di

import com.hariku.feature_profile.presentation.ProfileScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel {
        ProfileScreenViewModel(
            logoutUseCase = get(),
            getCurrentUserUseCase = get()
        )
    }
}