package com.hariku.feature_journal.data.local.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "prompt_cache")
data class PromptCacheEntity(
    @PrimaryKey
    val id: String = "latest",
    val introduction: String,
    val suggestionsJson: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface PromptDao {
    @Query("SELECT * FROM prompt_cache WHERE id = 'latest'")
    fun observeLatestPrompt(): Flow<PromptCacheEntity?>
    
    @Query("SELECT * FROM prompt_cache WHERE id = 'latest'")
    suspend fun getLatestPrompt(): PromptCacheEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cachePrompt(prompt: PromptCacheEntity)
    
    @Query("DELETE FROM prompt_cache")
    suspend fun clearCache()
}