package com.hariku.feature_chatbot.data.mapper

import com.hariku.feature_chatbot.data.dto.ChatbotDto
import com.hariku.feature_chatbot.domain.model.Chatbot

object ChatbotMapper {
    
    fun toDomain(dto: ChatbotDto, userId: String = ""): Chatbot {
        return Chatbot(
            id = dto.id.orEmpty(),
            name = dto.name.orEmpty(),
            subtitle = dto.subtitle.orEmpty(),
            description = dto.description.orEmpty(),
            avatarResId = dto.avatarResId ?: 0,
            createdAt = dto.createdAt ?: 0L,
            userId = userId
        )
    }
    
    fun toDto(model: Chatbot): ChatbotDto {
        return ChatbotDto(
            id = model.id,
            name = model.name,
            subtitle = model.subtitle,
            description = model.description,
            avatarResId = model.avatarResId,
            createdAt = model.createdAt
        )
    }
    
    fun toMap(model: Chatbot): Map<String, Any> {
        return mapOf(
            "id" to model.id,
            "name" to model.name,
            "subtitle" to model.subtitle,
            "description" to model.description,
            "avatarResId" to model.avatarResId,
            "createdAt" to model.createdAt
        )
    }
}