package com.hariku.feature_chatbot.data.mapper

import com.hariku.feature_chatbot.data.dto.ChatMessageDto
import com.hariku.feature_chatbot.domain.model.ChatMessage

/**
 * Mapper for converting between ChatMessage DTO and Domain model.
 */
object ChatMessageMapper {
    
    /**
     * Convert DTO to Domain model.
     */
    fun toDomain(dto: ChatMessageDto, userId: String = "", chatbotId: String = ""): ChatMessage {
        return ChatMessage(
            id = dto.id.orEmpty(),
            chatbotId = dto.chatbotId ?: chatbotId,
            userId = userId,
            message = dto.message.orEmpty(),
            isFromUser = dto.isFromUser ?: true,
            timestamp = dto.timestamp ?: 0L,
            isRead = dto.isRead ?: false
        )
    }
    
    /**
     * Convert Domain model to DTO.
     */
    fun toDto(model: ChatMessage): ChatMessageDto {
        return ChatMessageDto(
            id = model.id,
            chatbotId = model.chatbotId,
            message = model.message,
            isFromUser = model.isFromUser,
            timestamp = model.timestamp,
            isRead = model.isRead
        )
    }
}

