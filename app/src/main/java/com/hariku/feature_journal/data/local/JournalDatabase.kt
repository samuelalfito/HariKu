package com.hariku.feature_journal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hariku.feature_journal.data.local.dao.JournalDao
import com.hariku.feature_journal.data.local.dao.JournalOverviewDao
import com.hariku.feature_journal.data.local.dao.NoteDao
import com.hariku.feature_journal.data.local.dao.PromptCacheEntity
import com.hariku.feature_journal.data.local.dao.PromptDao
import com.hariku.feature_journal.data.local.entity.JournalEntryEntity
import com.hariku.feature_journal.data.local.entity.JournalOverviewEntity
import com.hariku.feature_journal.data.local.entity.NoteEntity
import com.hariku.feature_journal.data.local.entity.StickerElementEntity
import com.hariku.feature_journal.data.local.entity.TextElementEntity

@Database(
    entities = [
        JournalEntryEntity::class,
        StickerElementEntity::class,
        TextElementEntity::class,
        PromptCacheEntity::class,
        NoteEntity::class,
        JournalOverviewEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    abstract fun promptDao(): PromptDao
    abstract fun noteDao(): NoteDao
    abstract fun journalOverviewDao(): JournalOverviewDao
    
    companion object {
        const val DATABASE_NAME = "journal_db"
    }
}
