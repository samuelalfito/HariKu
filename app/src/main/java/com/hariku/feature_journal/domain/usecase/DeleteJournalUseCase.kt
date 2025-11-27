package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.repository.JournalRepository

class DeleteJournalUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(journalId: Long) {
        repository.deleteJournal(journalId)
    }
}