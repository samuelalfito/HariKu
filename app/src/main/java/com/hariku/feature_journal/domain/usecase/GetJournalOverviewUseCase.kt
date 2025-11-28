package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.JournalOverview
import com.hariku.feature_journal.domain.repository.JournalOverviewRepository
import kotlinx.coroutines.flow.Flow

class GetJournalOverviewUseCase(
    private val repository: JournalOverviewRepository
) {
    operator fun invoke(journalId: String): Flow<JournalOverview?> {
        return repository.observeOverview(journalId)
    }
}

