package com.hariku.feature_journal.domain.model

data class Note(
    val id: String = "",
    val journalId: String,
    val userId: String,
    val title: String,
    val content: String,
    val imageUrls: List<String> = emptyList(),
    val dateTime: Long,
    val createdAt: Long = System.currentTimeMillis()
)

