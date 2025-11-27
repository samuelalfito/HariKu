package com.hariku.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_article.di.articleModule
import com.hariku.feature_auth.di.authModule
import com.hariku.feature_chatbot.di.chatbotModule
import com.hariku.feature_home.di.homeModule
import com.hariku.feature_meditation.di.meditationModule
import com.hariku.feature_onboarding.di.onboardingModule
import com.hariku.feature_pin.di.pinModule
import com.hariku.feature_profile.di.profileModule
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
    homeModule,
    onboardingModule,
    profileModule,
    chatbotModule,
    meditationModule,
    articleModule
)