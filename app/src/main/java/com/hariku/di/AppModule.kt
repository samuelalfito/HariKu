package com.hariku.di

import com.hariku.feature_pin.data.local.PinDatabase
import com.hariku.feature_pin.di.pinModule
import com.hariku.feature_pin.domain.repository.PinRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = listOf(
    pinModule
)