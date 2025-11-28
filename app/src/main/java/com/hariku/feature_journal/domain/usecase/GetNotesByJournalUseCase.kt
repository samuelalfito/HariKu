package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.Note
import com.hariku.feature_journal.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesByJournalUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(journalId: String): Flow<List<Note>> {
        return repository.observeNotesByJournalId(journalId)
    }
}

