package com.hariku.feature_journal.data.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.hariku.feature_journal.data.local.dao.JournalDao
import com.hariku.feature_journal.data.local.entity.JournalEntryEntity
import com.hariku.feature_journal.data.local.entity.JournalFullEntity
import com.hariku.feature_journal.data.local.entity.StickerElementEntity
import com.hariku.feature_journal.data.local.entity.TextElementEntity

@Database(
    entities = [
        JournalEntryEntity::class,
        StickerElementEntity::class,
        TextElementEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(JournalDatabase.ComposeTypeConverters::class)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao

    companion object {
        const val DATABASE_NAME = "journal_db"
    }

    /**
     * Type Converters untuk mengkonversi tipe data Compose (Color, TextAlign)
     * menjadi tipe data yang dapat disimpan di Room.
     */
    class ComposeTypeConverters {

        // Konverter untuk androidx.compose.ui.graphics.Color
        @TypeConverter
        fun fromColor(color: Color): Long {
            // Konversi Color ke Long ARGB
            return color.value.toLong()
        }

        @TypeConverter
        fun toColor(colorLong: Long): Color {
            // Konversi Long ARGB kembali ke Color
            return Color(colorLong)
        }

        // Konverter untuk androidx.compose.ui.text.style.TextAlign
        @TypeConverter
        fun fromTextAlign(textAlign: TextAlign): String {
            // FIX: Menggunakan 'when' untuk memetakan objek TextAlign ke String
            return when (textAlign) {
                TextAlign.Start -> "Start"
                TextAlign.End -> "End"
                TextAlign.Center -> "Center"
                TextAlign.Justify -> "Justify"
                else -> "Start" // Default
            }
        }

        @TypeConverter
        fun toTextAlign(textAlignString: String): TextAlign {
            // FIX: Menggunakan 'when' untuk memetakan String kembali ke objek TextAlign
            return when (textAlignString) {
                "End" -> TextAlign.End
                "Center" -> TextAlign.Center
                "Justify" -> TextAlign.Justify
                "Start" -> TextAlign.Start
                else -> TextAlign.Start // Default jika gagal
            }
        }
    }
}