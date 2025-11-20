package com.hariku.feature_chatbot.domain.usecase

import com.hariku.feature_chatbot.domain.model.ChatbotWithHistory
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting chatbots with their chat history.
 * Single responsibility: Retrieve chatbots with last message and unread count.
 */
class GetChatbotsWithHistoryUseCase(
    private val repository: ChatbotRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<ChatbotWithHistory>>> {
        return repository.getChatbotsWithHistory(userId)
    }
}

