package com.hariku.feature_journal.domain.model

data class PromptSuggestion(
    val id: String,
    val text: String,
    val isSelected: Boolean = false
)