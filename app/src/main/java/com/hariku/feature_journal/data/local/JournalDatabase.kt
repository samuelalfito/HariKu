package com.hariku.feature_journal.data.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.hariku.feature_journal.data.local.dao.JournalDao
import com.hariku.feature_journal.data.local.dao.PromptCacheEntity
import com.hariku.feature_journal.data.local.dao.PromptDao
import com.hariku.feature_journal.data.local.entity.JournalEntryEntity
import com.hariku.feature_journal.data.local.entity.StickerElementEntity
import com.hariku.feature_journal.data.local.entity.TextElementEntity

@Database(
    entities = [
        JournalEntryEntity::class,
        StickerElementEntity::class,
        TextElementEntity::class,
        PromptCacheEntity::class],
    version = 2
)
@TypeConverters(ComposeTypeConverters::class)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    abstract fun promptDao(): PromptDao
    companion object {
        const val DATABASE_NAME = "journal_db"
    }
}

class ComposeTypeConverters {
    
    @TypeConverter
    fun fromColor(color: Color): Long {
        return color.value.toLong()
    }

    @TypeConverter
    fun toColor(colorLong: Long): Color {
        return Color(colorLong)
    }

    @TypeConverter
    fun fromTextAlign(textAlign: TextAlign): String {
        return when (textAlign) {
            TextAlign.Start -> "Start"
            TextAlign.End -> "End"
            TextAlign.Center -> "Center"
            TextAlign.Justify -> "Justify"
            else -> "Start"
        }
    }

    @TypeConverter
    fun toTextAlign(textAlignString: String): TextAlign {
        return when (textAlignString) {
            "End" -> TextAlign.End
            "Center" -> TextAlign.Center
            "Justify" -> TextAlign.Justify
            "Start" -> TextAlign.Start
            else -> TextAlign.Start
        }
    }
}