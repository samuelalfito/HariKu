package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.JournalOverview
import kotlinx.coroutines.flow.Flow

interface JournalOverviewRepository {
    suspend fun generateOverview(journalId: String, notes: List<String>): Result<JournalOverview>
    suspend fun getOverview(journalId: String): JournalOverview?
    fun observeOverview(journalId: String): Flow<JournalOverview?>
    suspend fun saveOverview(overview: JournalOverview): Result<Unit>
}

