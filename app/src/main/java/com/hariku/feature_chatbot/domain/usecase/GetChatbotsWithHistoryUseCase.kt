package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.ChatbotWithHistory
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import kotlinx.coroutines.flow.Flow

class GetChatbotsWithHistoryUseCase(
    private val repository: ChatbotRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<ChatbotWithHistory>>> {
        return repository.getChatbotsWithHistory(userId)
    }
}

