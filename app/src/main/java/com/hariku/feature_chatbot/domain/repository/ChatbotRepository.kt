package com.hariku.feature_chatbot.domain.repository

import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.model.ChatMessage
import com.hariku.feature_chatbot.domain.model.ChatbotWithHistory
import kotlinx.coroutines.flow.Flow

interface ChatbotRepository {
    
    fun getChatbots(userId: String): Flow<Result<List<Chatbot>>>
    
    fun getChatbotsWithHistory(userId: String): Flow<Result<List<ChatbotWithHistory>>>
    
    suspend fun addChatbot(chatbot: Chatbot): Result<Unit>
    
    suspend fun deleteChatbot(chatbotId: String, userId: String): Result<Unit>
    
    suspend fun getChatbotById(chatbotId: String, userId: String): Result<Chatbot>
    
    fun getChatMessages(chatbotId: String, userId: String): Flow<Result<List<ChatMessage>>>
    
    suspend fun sendMessage(message: ChatMessage): Result<Unit>
    
    suspend fun markMessagesAsRead(chatbotId: String, userId: String): Result<Unit>
}