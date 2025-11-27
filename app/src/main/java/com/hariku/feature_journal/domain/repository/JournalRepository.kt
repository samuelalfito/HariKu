package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

/**
 * Interface Repository (Kontrak Domain) bisa lokal/remote
 */
interface JournalRepository {

    /**
     * Mengambil semua jurnal milik user tertentu secara real-time.
     * @param userId ID pengguna Firebase.
     * @return Flow dari daftar JournalEntry.
     */
    fun getAllJournals(userId: String): Flow<List<JournalEntry>>

    /**
     * Mengambil jurnal berdasarkan ID-nya.
     * @param journalId ID jurnal.
     * @return JournalEntry atau null jika tidak ditemukan.
     */
    suspend fun getJournalById(journalId: Long): JournalEntry?

    /**
     * Menyimpan (membuat atau memperbarui) sebuah jurnal.
     * @param journal Jurnal yang akan disimpan.
     * @return ID jurnal yang baru dibuat/diperbarui.
     */
    suspend fun saveJournal(journal: JournalEntry): Long

    /**
     * Menghapus jurnal berdasarkan ID.
     * @param journalId ID jurnal yang akan dihapus.
     */
    suspend fun deleteJournal(journalId: Long)
}