package com.hariku.di

import com.hariku.feature_home.di.homeModule
import com.hariku.feature_pin.di.pinModule

val appModule = listOf(
    pinModule,
    homeModule
)