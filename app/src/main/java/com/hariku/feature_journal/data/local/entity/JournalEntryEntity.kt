package com.hariku.feature_journal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val journalId: Long = 0,
    val userId: String, // Foreign Key: ID User Firebase
    val title: String,
    val bgRes: Int,
    val date: String,
    val description: String? = null
)
