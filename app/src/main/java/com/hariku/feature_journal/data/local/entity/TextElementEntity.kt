package com.hariku.feature_journal.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "text_elements",
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
data class TextElementEntity(
    @PrimaryKey(autoGenerate = true)
    val textId: Long = 0,
    val parentJournalId: Long, // Foreign Key ke JournalEntryEntity
    val text: String,
    val offsetX: Float,
    val offsetY: Float,
    val fontSize: Float = 24f,
    val colorLong: Long, // Menyimpan Color sebagai Long (ARGB)
    val fontFamily: String = "Default",
    val textAlignString: String, // Menyimpan TextAlign sebagai String
    val isUnderlined: Boolean = false,
    val shadowX: Float = 0f,
    val shadowY: Float = 0f,
    val shadowRadius: Float = 12f,
    val shadowColorLong: Long, // Menyimpan Color sebagai Long (ARGB)
    val shadowOpacity: Float = 1f,
    val outlineWidth: Float = 0f,
    val outlineColorLong: Long, // Menyimpan Color sebagai Long (ARGB)
    val scale: Float = 1f
)
