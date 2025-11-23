package com.hariku.feature_meditation.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationSongDao {
    
    @Query("SELECT * FROM meditation_songs ORDER BY category, title")
    suspend fun getAllSongs(): List<MeditationSongEntity>
    
    @Query("SELECT * FROM meditation_songs ORDER BY category, title")
    fun observeAllSongs(): Flow<List<MeditationSongEntity>>
    
    @Query("SELECT * FROM meditation_songs WHERE id = :songId LIMIT 1")
    suspend fun getSongById(songId: String): MeditationSongEntity?
    
    @Query("SELECT * FROM meditation_songs WHERE category = :category ORDER BY title")
    suspend fun getSongsByCategory(category: String): List<MeditationSongEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songs: List<MeditationSongEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: MeditationSongEntity)
    
    @Query("DELETE FROM meditation_songs")
    suspend fun deleteAllSongs()
}