package com.hariku.feature_journal.domain.usecase

data class JournalUseCases(
    val saveJournal: SaveJournalUseCase,
    val deleteJournal: DeleteJournalUseCase,
    val getAllJournals: GetAllJournalsUseCase,
    // val getJournalsByUserId: GetJournalsByUserIdUseCase
)