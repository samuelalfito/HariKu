package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.repository.ChatbotRepository

class MarkMessagesAsReadUseCase(
    private val repository: ChatbotRepository
) {
    suspend operator fun invoke(chatbotId: String, userId: String): Result<Unit> {
        return repository.markMessagesAsRead(chatbotId, userId)
    }
}