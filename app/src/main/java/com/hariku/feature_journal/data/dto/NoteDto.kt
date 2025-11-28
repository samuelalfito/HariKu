package com.hariku.feature_journal.data.dto

data class NoteDto(
    val id: String? = null,
    val journalId: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val content: String? = null,
    val imageUrls: List<String>? = null,
    val dateTime: Long? = null,
    val createdAt: Long? = null
)

