package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.repository.JournalRepository

class DeleteJournalUseCase(
    private val repository: JournalRepository
) {
    /**
     * Menghapus Journal dari penyimpanan.
     * @param journalId ID dari Journal yang akan dihapus.
     */
    suspend operator fun invoke(journalId: String) {
        if (journalId.isBlank()) {
            throw IllegalArgumentException("Journal ID cannot be blank for deletion.")
        }

        repository.deleteJournal(journalId)
    }
}