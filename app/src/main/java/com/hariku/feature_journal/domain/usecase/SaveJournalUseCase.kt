package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.model.JournalEntry

class SaveJournalUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(journal: JournalEntry): Long {
        // tambahan bisa disini, misal: validasi title tidak boleh kosong tambahin di sini.
        if (journal.title.isBlank()) {
            throw IllegalArgumentException("Judul jurnal tidak boleh kosong.")
        }
        return repository.saveJournal(journal)
    }
}