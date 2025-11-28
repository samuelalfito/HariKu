package com.hariku.feature_journal.data.dto

data class JournalOverviewDto(
    val journalId: String? = null,
    val overview: String? = null,
    val noteCount: Int? = null,
    val lastUpdated: Long? = null,
    val generatedAt: Long? = null
)

