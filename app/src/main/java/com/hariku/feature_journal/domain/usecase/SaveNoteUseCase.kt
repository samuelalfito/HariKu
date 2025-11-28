package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.Note
import com.hariku.feature_journal.domain.repository.NoteRepository

class SaveNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Unit> {
        return repository.saveNote(note)
    }
}

