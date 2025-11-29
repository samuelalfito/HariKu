package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository

class GetChatbotByIdUseCase(
    private val repository: ChatbotRepository
) {
    suspend operator fun invoke(chatbotId: String, userId: String): Result<Chatbot> {
        return repository.getChatbotById(chatbotId, userId)
    }
}