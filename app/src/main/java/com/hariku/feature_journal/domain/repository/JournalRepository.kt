package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {

    suspend fun saveJournal(journal: Journal)

    suspend fun deleteJournal(journalId: String)

    /**
     * Mengambil semua jurnal milik pengguna yang sedang login.
     */
    fun getAllJournals(): Flow<List<Journal>>

    /**
     * Mengambil semua jurnal milik pengguna tertentu
     */
    suspend fun getJournalsByUserId(userId: String): List<Journal>
}