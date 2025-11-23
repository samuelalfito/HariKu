package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import kotlinx.coroutines.flow.Flow

class GetChatbotsUseCase(
    private val repository: ChatbotRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<Chatbot>>> {
        return repository.getChatbots(userId)
    }
}