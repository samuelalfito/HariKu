package com.hariku.feature_chatbot.data.repository

import com.hariku.feature_chatbot.data.mapper.ChatbotMapper
import com.hariku.feature_chatbot.data.remote.ChatbotFirebaseService
import com.hariku.feature_chatbot.domain.model.Chatbot
import com.hariku.feature_chatbot.domain.repository.ChatbotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ChatbotRepositoryImpl(
    private val firebaseService: ChatbotFirebaseService,
    private val mapper: ChatbotMapper
) : ChatbotRepository {
    
    override fun getChatbots(userId: String): Flow<Result<List<Chatbot>>> {
        return firebaseService.getChatbots(userId)
            .map { dtoList ->
                val chatbots = dtoList.map { mapper.toDomain(it, userId) }
                Result.success(chatbots)
            }
            .catch { exception ->
                emit(Result.failure(exception))
            }
    }
    
    override suspend fun addChatbot(chatbot: Chatbot): Result<Unit> {
        return try {
            val dto = mapper.toDto(chatbot)
            firebaseService.addChatbot(dto, chatbot.userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteChatbot(chatbotId: String, userId: String): Result<Unit> {
        return try {
            firebaseService.deleteChatbot(chatbotId, userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getChatbotById(chatbotId: String, userId: String): Result<Chatbot> {
        return try {
            val dto = firebaseService.getChatbotById(chatbotId, userId)
            if (dto != null) {
                Result.success(mapper.toDomain(dto, userId))
            } else {
                Result.failure(Exception("Chatbot not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}