package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.Journal

interface JournalRepository {

    suspend fun saveJournal(journal: Journal)

    suspend fun deleteJournal(journalId: String)

    /**
     * Mengambil semua jurnal milik pengguna yang sedang login.
     */
    suspend fun getAllJournals(): List<Journal>

    /**
     * Mengambil semua jurnal milik pengguna tertentu
     */
    suspend fun getJournalsByUserId(userId: String): List<Journal>
}