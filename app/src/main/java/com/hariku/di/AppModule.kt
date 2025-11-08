package com.hariku.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_auth.presentation.login.di.loginModule
import com.hariku.feature_auth.presentation.register.di.registerModule
import com.hariku.feature_pin.di.pinModule
import org.koin.dsl.module

val appModule = listOf(
    module {
        single {
            FirebaseAuth.getInstance()
        }
    },
    module {
        single {
            FirebaseFirestore.getInstance()
        }
    },
    pinModule,
    loginModule,
    registerModule
)