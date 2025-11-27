package com.hariku.feature_journal.domain.model

data class PromptResponse(
    val introduction: String,
    val suggestions: List<PromptSuggestion>
)