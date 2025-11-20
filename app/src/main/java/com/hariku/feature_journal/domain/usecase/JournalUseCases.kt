package com.hariku.feature_journal.domain.usecase

data class JournalUseCases(
    val getAllJournals: GetAllJournalsUseCase,
    val getJournalById: GetJournalByIdUseCase,
    val saveJournal: SaveJournalUseCase,
    val deleteJournal: DeleteJournalUseCase
)
