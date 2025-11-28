package com.hariku.feature_journal.data.repository

import com.hariku.feature_journal.data.local.dao.NoteDao
import com.hariku.feature_journal.data.mapper.NoteMapper
import com.hariku.feature_journal.data.remote.NoteRemoteDataSource
import com.hariku.feature_journal.domain.model.Note
import com.hariku.feature_journal.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val remoteDataSource: NoteRemoteDataSource,
    private val noteDao: NoteDao,
    private val mapper: NoteMapper
) : NoteRepository {

    override suspend fun saveNote(note: Note): Result<Unit> {
        return try {
            val dto = mapper.toDto(note)
            remoteDataSource.saveNote(dto)
            
            val entity = mapper.toEntity(note)
            noteDao.insertNote(entity)
            
            Result.success(Unit)
        } catch (e: Exception) {
            try {
                val entity = mapper.toEntity(note)
                noteDao.insertNote(entity)
                Result.success(Unit)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }

    override suspend fun deleteNote(noteId: String): Result<Unit> {
        return try {
            remoteDataSource.deleteNote(noteId)
            noteDao.deleteNote(noteId)
            Result.success(Unit)
        } catch (e: Exception) {
            try {
                noteDao.deleteNote(noteId)
                Result.success(Unit)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }

    override suspend fun getNotesByJournalId(journalId: String): List<Note> {
        return try {
            val remoteDtos = remoteDataSource.getNotesByJournalId(journalId)
            val notes = remoteDtos.map { mapper.toDomain(it) }
            
            notes.forEach { note ->
                noteDao.insertNote(mapper.toEntity(note))
            }
            
            notes
        } catch (e: Exception) {
            val localEntities = noteDao.getNotesByJournalIdSync(journalId)
            localEntities.map { mapper.fromEntity(it) }
        }
    }

    override fun observeNotesByJournalId(journalId: String): Flow<List<Note>> {
        return noteDao.getNotesByJournalId(journalId).map { entities ->
            entities.map { mapper.fromEntity(it) }
        }
    }
}

