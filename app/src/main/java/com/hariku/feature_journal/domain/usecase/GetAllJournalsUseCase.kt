package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.repository.JournalRepository
import com.hariku.feature_journal.domain.model.JournalEntry
import kotlinx.coroutines.flow.Flow

class GetAllJournalsUseCase(private val repository: JournalRepository) {
    operator fun invoke(userId: String): Flow<List<JournalEntry>> {
        return repository.getAllJournals(userId)
    }
}