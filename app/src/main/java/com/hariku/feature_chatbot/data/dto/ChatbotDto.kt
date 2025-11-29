package com.hariku.feature_chatbot.data.dto

import com.google.firebase.firestore.PropertyName

data class ChatbotDto(
    @PropertyName("id")
    val id: String? = null,
    
    @PropertyName("name")
    val name: String? = null,
    
    @PropertyName("subtitle")
    val subtitle: String? = null,
    
    @PropertyName("description")
    val description: String? = null,
    
    @PropertyName("avatarResId")
    val avatarResId: Int? = null,
    
    @PropertyName("createdAt")
    val createdAt: Long? = null
)