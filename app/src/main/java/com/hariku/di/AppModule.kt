package com.hariku.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_auth.di.authModule
import com.hariku.feature_home.di.homeModule
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
    authModule,
    homeModule
)