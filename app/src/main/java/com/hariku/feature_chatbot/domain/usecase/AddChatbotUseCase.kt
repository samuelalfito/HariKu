package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository

class AddChatbotUseCase(
    private val repository: ChatbotRepository
) {
    suspend operator fun invoke(chatbot: Chatbot): Result<Unit> {
        return repository.addChatbot(chatbot)
    }
}