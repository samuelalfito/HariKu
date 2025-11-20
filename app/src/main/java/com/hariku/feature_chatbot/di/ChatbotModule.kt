package com.hariku.feature_chatbot.di

import com.hariku.feature_chatbot.data.mapper.ChatMessageMapper
import com.hariku.feature_chatbot.data.mapper.ChatbotMapper
import com.hariku.feature_chatbot.data.remote.ChatbotFirebaseService
import com.hariku.feature_chatbot.data.repository.ChatbotRepositoryImpl
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import com.hariku.feature_chatbot.domain.usecase.AddChatbotUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotByIdUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsWithHistoryUseCase
import com.hariku.feature_chatbot.presentation.ChatbotViewModel
import com.hariku.feature_chatbot.presentation.customize.CustomizeCatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chatbotModule = module {
    
    single {
        ChatbotMapper
    }
    
    single {
        ChatMessageMapper
    }
    
    single {
        ChatbotFirebaseService(
            firestore = get()
        )
    }
    
    single<ChatbotRepository> {
        ChatbotRepositoryImpl(
            firebaseService = get(),
            mapper = get(),
            messageMapper = get()
        )
    }
    
    factory {
        AddChatbotUseCase(repository = get())
    }
    
    factory {
        GetChatbotsUseCase(repository = get())
    }
    
    factory {
        GetChatbotsWithHistoryUseCase(repository = get())
    }
    
    factory {
        GetChatbotByIdUseCase(repository = get())
    }
    
    viewModel {
        CustomizeCatViewModel(
            addChatbotUseCase = get(),
            firebaseAuth = get()
        )
    }
    
    viewModel {
        ChatbotViewModel(
            getChatbotsWithHistoryUseCase = get(),
            firebaseAuth = get()
        )
    }
}