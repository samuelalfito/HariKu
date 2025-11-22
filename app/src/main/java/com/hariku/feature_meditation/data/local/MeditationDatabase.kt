package com.hariku.feature_meditation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MeditationSongEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MeditationDatabase : RoomDatabase() {
    abstract fun meditationSongDao(): MeditationSongDao
}

