package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.repository.JournalRepository

class SaveJournalUseCase(
    private val repository: JournalRepository
) {
    /**
     * Menyimpan atau MEMPERBARUI objek Journal ke penyimpanan.
     * @param journal Objek Journal yang akan disimpan.
     */
    suspend operator fun invoke(journal: Journal) {
        // tambahan bisa disini misalnya memastikan ID dan userId ada sebelum menyimpan
        if (journal.id.isBlank() || journal.userId.isBlank()) {
            throw IllegalArgumentException("Journal ID and User ID cannot be blank.")
        }

        repository.saveJournal(journal)
    }
}