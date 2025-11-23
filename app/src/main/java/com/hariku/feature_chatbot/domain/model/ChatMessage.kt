package com.hariku.feature_chatbot.domain.model

/**
 * Domain model for Chat Message.
 * Represents a single message in a conversation with a chatbot.
 */
data class ChatMessage(
    val id: String = "",
    val chatbotId: String = "",
    val userId: String = "",
    val message: String = "",
    val isFromUser: Boolean = true,
    val timestamp: Long = 0L,
    val isRead: Boolean = false
)

