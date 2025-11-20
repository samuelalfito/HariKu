package com.hariku.feature_chatbot.domain.model

/**
 * Domain model for Chatbot with chat history.
 * Combines chatbot data with last message and unread count.
 */
data class ChatbotWithHistory(
    val chatbot: Chatbot,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val unreadCount: Int = 0
)

