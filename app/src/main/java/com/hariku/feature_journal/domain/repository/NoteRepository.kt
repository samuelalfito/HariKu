package com.hariku.feature_journal.domain.repository

import com.hariku.feature_journal.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun saveNote(note: Note): Result<Unit>
    suspend fun deleteNote(noteId: String): Result<Unit>
    suspend fun getNotesByJournalId(journalId: String): List<Note>
    fun observeNotesByJournalId(journalId: String): Flow<List<Note>>
}

