package com.hariku.feature_journal.domain.model

data class JournalOverview(
    val journalId: String,
    val overview: String,
    val noteCount: Int,
    val lastUpdated: Long,
    val generatedAt: Long = System.currentTimeMillis()
)

