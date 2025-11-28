package com.hariku.feature_journal.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sticker_elements",
    foreignKeys = [
        ForeignKey(
            entity = JournalEntryEntity::class,
            parentColumns = ["journalId"],
            childColumns = ["parentJournalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["parentJournalId"])]
)
data class StickerElementEntity(
    @PrimaryKey(autoGenerate = true)
    val stickerId: Long = 0,
    val parentJournalId: Long, // Foreign Key ke JournalEntryEntity
    val emoji: String,
    val offsetX: Float,
    val offsetY: Float,
    val scale: Float = 1f,
    val rotation: Float = 0f
)
