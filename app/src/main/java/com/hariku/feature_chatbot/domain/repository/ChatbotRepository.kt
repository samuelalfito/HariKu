package com.hariku.feature_chatbot.domain.repository

import com.hariku.feature_chatbot.domain.model.Chatbot
import kotlinx.coroutines.flow.Flow

interface ChatbotRepository {
    
    fun getChatbots(userId: String): Flow<Result<List<Chatbot>>>
    
    suspend fun addChatbot(chatbot: Chatbot): Result<Unit>
    
    suspend fun deleteChatbot(chatbotId: String, userId: String): Result<Unit>
    
    suspend fun getChatbotById(chatbotId: String, userId: String): Result<Chatbot>
}