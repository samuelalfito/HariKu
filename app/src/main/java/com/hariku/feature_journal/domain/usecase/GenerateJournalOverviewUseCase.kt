package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.JournalOverview
import com.hariku.feature_journal.domain.repository.JournalOverviewRepository

class GenerateJournalOverviewUseCase(
    private val repository: JournalOverviewRepository
) {
    suspend operator fun invoke(journalId: String, notes: List<String>): Result<JournalOverview> {
        return repository.generateOverview(journalId, notes)
    }
}

