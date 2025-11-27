package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.ChatMessage
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository

class SendMessageUseCase(
    private val repository: ChatbotRepository
) {
    suspend operator fun invoke(message: ChatMessage): Result<Unit> {
        return repository.sendMessage(message)
    }
}