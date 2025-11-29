package com.hariku.feature_journal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val id: String,
    val journalId: String,
    val userId: String,
    val title: String,
    val content: String,
    val imageUrls: String,
    val dateTime: Long,
    val createdAt: Long
)

