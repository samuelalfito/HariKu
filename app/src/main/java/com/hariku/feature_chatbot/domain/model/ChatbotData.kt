package com.hariku.feature_chatbot.domain.model

data class ChatbotData(
    val id: Int,
    val avatarResId: Int,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int,
)