package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.repository.JournalRepository

class GetAllJournalsUseCase(
    private val repository: JournalRepository
) {
    /**
     * Mengambil semua Journal milik pengguna saat ini.
     * @return List<Journal>
     */
    suspend operator fun invoke(): List<Journal> {
        // Logika tambahan mungkin bisa filter atau diurutkan misalnya tambah aja disini
        val journals = repository.getAllJournals()

        // Contoh: Mengurutkan berdasarkan tanggal
        return journals.sortedByDescending { it.date }
    }
}