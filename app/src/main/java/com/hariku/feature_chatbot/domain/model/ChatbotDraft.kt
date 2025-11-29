package com.hariku.feature_chatbot.domain.model

data class ChatbotDraft(
    val name: String = "",
    val avatarResId: Int = 0,
    val isValid: Boolean = false
) {
    companion object {
        fun create(name: String, avatarResId: Int): ChatbotDraft {
            return ChatbotDraft(
                name = name.trim(),
                avatarResId = avatarResId,
                isValid = name.trim().isNotBlank() && avatarResId != 0
            )
        }
    }
}