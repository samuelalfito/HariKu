package com.hariku.feature_journal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_overviews")
data class JournalOverviewEntity(
    @PrimaryKey
    val journalId: String,
    val overview: String,
    val noteCount: Int,
    val lastUpdated: Long,
    val generatedAt: Long
)

