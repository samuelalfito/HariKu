package com.hariku.feature_journal.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class JournalFullEntity(
    @Embedded
    val entry: JournalEntryEntity,

    @Relation(
        parentColumn = "journalId",
        entityColumn = "parentJournalId"
    )
    val stickerElements: List<StickerElementEntity>,

    @Relation(
        parentColumn = "journalId",
        entityColumn = "parentJournalId"
    )
    val textElements: List<TextElementEntity>
)
