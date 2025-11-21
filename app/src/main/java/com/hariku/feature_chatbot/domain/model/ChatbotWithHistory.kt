package com.hariku.feature_chatbot.domain.model

data class ChatbotWithHistory(
    val chatbot: Chatbot,
    val lastMessage: String = "",
    val lastMessageTime: Long = 0L,
    val unreadCount: Int = 0
)

