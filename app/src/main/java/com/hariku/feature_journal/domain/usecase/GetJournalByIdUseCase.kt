package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.model.JournalEntry

class GetJournalByIdUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(journalId: Long): JournalEntry? {
        return repository.getJournalById(journalId)
    }
}