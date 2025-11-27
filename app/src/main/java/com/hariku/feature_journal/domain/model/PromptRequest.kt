package com.hariku.feature_journal.domain.model

data class PromptRequest(
    val selectedFeelings: List<String>,
    val customFeeling: String = ""
)
