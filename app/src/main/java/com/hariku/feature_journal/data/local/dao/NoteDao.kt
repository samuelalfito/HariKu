package com.hariku.feature_journal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hariku.feature_journal.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE journalId = :journalId ORDER BY dateTime DESC")
    fun getNotesByJournalId(journalId: String): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: String)

    @Query("SELECT * FROM notes WHERE journalId = :journalId ORDER BY dateTime DESC")
    suspend fun getNotesByJournalIdSync(journalId: String): List<NoteEntity>
}

