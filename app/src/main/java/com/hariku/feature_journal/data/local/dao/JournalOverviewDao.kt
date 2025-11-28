package com.hariku.feature_journal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hariku.feature_journal.data.local.entity.JournalOverviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalOverviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOverview(overview: JournalOverviewEntity)

    @Query("SELECT * FROM journal_overviews WHERE journalId = :journalId")
    fun getOverviewByJournalId(journalId: String): Flow<JournalOverviewEntity?>

    @Query("SELECT * FROM journal_overviews WHERE journalId = :journalId")
    suspend fun getOverviewByJournalIdSync(journalId: String): JournalOverviewEntity?

    @Query("DELETE FROM journal_overviews WHERE journalId = :journalId")
    suspend fun deleteOverview(journalId: String)
}

