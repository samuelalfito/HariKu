package com.hariku.feature_chatbot.data.mapper

import com.hariku.feature_chatbot.data.dto.ChatMessageDto
import com.hariku.feature_chatbot.domain.model.ChatMessage

object ChatMessageMapper {
    
    fun toDomain(dto: ChatMessageDto, userId: String = "", chatbotId: String = ""): ChatMessage {
        return ChatMessage(
            id = dto.id.ifEmpty { "" },
            chatbotId = dto.chatbotId.ifEmpty { chatbotId },
            userId = userId,
            message = dto.message,
            isFromUser = dto.fromUser,
            timestamp = dto.timestamp,
            isRead = dto.read
        )
    }
    
    fun toDto(model: ChatMessage): ChatMessageDto {
        return ChatMessageDto(
            id = model.id,
            chatbotId = model.chatbotId,
            message = model.message,
            fromUser = model.isFromUser,
            timestamp = model.timestamp,
            read = model.isRead
        )
    }
}

