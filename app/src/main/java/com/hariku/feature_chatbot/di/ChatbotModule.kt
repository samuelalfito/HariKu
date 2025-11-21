package com.hariku.feature_chatbot.di

import com.hariku.feature_chatbot.data.mapper.ChatMessageMapper
import com.hariku.feature_chatbot.data.mapper.ChatbotMapper
import com.hariku.feature_chatbot.data.remote.ChatbotFirebaseService
import com.hariku.feature_chatbot.data.remote.GeminiApiService
import com.hariku.feature_chatbot.data.repository.ChatbotRepositoryImpl
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import com.hariku.feature_chatbot.domain.usecase.AddChatbotUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotByIdUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatbotsWithHistoryUseCase
import com.hariku.feature_chatbot.domain.usecase.GetChatMessagesUseCase
import com.hariku.feature_chatbot.domain.usecase.MarkMessagesAsReadUseCase
import com.hariku.feature_chatbot.domain.usecase.SendMessageUseCase
import com.hariku.feature_chatbot.presentation.ChatbotViewModel
import com.hariku.feature_chatbot.presentation.customize.CustomizeCatViewModel
import com.hariku.feature_chatbot.presentation.customize.CustomizeNewCatViewModel
import com.hariku.feature_chatbot.presentation.customize.CustomizePersonalCatViewModel
import com.hariku.feature_chatbot.presentation.detail.ChatbotDetailViewModel
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
    
    single {
        val apiKey = com.hariku.BuildConfig.GEMINI_API_KEY
        GeminiApiService(apiKey)
    }
    
    single<ChatbotRepository> {
        ChatbotRepositoryImpl(
            firebaseService = get(),
            geminiApiService = get(),
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
    
    factory {
        GetChatMessagesUseCase(repository = get())
    }
    
    factory {
        SendMessageUseCase(repository = get())
    }
    
    factory {
        MarkMessagesAsReadUseCase(repository = get())
    }
    
    viewModel {
        CustomizeCatViewModel(
            addChatbotUseCase = get(),
            firebaseAuth = get()
        )
    }
    
    viewModel {
        CustomizeNewCatViewModel()
    }
    
    viewModel {
        CustomizePersonalCatViewModel(
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
    
    viewModel {
        ChatbotDetailViewModel(
            getChatMessagesUseCase = get(),
            getChatbotByIdUseCase = get(),
            sendMessageUseCase = get(),
            markMessagesAsReadUseCase = get(),
            firebaseAuth = get()
        )
    }
}