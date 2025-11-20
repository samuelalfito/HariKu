package com.hariku.feature_chatbot.data.dto

import com.google.firebase.firestore.PropertyName

/**
 * DTO for Chat Message from Firestore.
 * Matches Firebase document structure with nullable properties.
 */
data class ChatMessageDto(
    @PropertyName("id")
    val id: String? = null,
    
    @PropertyName("chatbotId")
    val chatbotId: String? = null,
    
    @PropertyName("message")
    val message: String? = null,
    
    @PropertyName("isFromUser")
    val isFromUser: Boolean? = null,
    
    @PropertyName("timestamp")
    val timestamp: Long? = null,
    
    @PropertyName("isRead")
    val isRead: Boolean? = null
)

