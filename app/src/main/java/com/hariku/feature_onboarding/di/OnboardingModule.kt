package com.hariku.feature_onboarding.di

import com.hariku.feature_onboarding.presentation.SplashScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {

    // Memberi tahu Koin cara membuat SplashViewModel
    // 'viewModel' adalah kata kunci khusus dari Koin untuk ViewModel Android
    viewModel {
        SplashScreenViewModel(get())
    }
}