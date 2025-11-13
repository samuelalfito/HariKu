package com.hariku.feature_pin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hariku.feature_pin.data.local.dao.PinDao
import com.hariku.feature_pin.data.local.entity.Pin

@Database(entities = [Pin::class], version = 1, exportSchema = false)
abstract class PinDatabase : RoomDatabase() {
    abstract fun pinDao(): PinDao
    companion object {
        @Volatile
        private var INSTANCE: PinDatabase? = null
        fun getDatabase(context: Context): PinDatabase {
            // Jika INSTANCE sudah ada, kembalikan INSTANCE
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PinDatabase::class.java,
                    "pin_database"
                ).build()
                INSTANCE = instance
                // Kembalikan instance yang baru dibuat
                instance
            }
        }
    }
}