package com.hariku.feature_pin.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.hariku.feature_pin.data.local.PinDatabase
import com.hariku.feature_pin.domain.repository.PinRepository
import com.hariku.feature_pin.presentation.PinScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pinModule = module {
    single { PinDatabase.getDatabase(androidContext()).pinDao() }
    single { PinRepository(get()) }
    viewModel {
        PinScreenViewModel(get())
    }
}