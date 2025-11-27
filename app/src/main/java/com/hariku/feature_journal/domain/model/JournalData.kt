package com.hariku.feature_journal.domain.model

data class JournalData(
    val id: Int,
    val title: String,
    val date: String,
    val desc: String? = null
)