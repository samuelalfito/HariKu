package com.hariku.feature_journal.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_journal.data.dto.NoteDto
import kotlinx.coroutines.tasks.await

class NoteRemoteDataSource(
    private val firestore: FirebaseFirestore
) {
    private val notesCollection = firestore.collection("notes")

    suspend fun saveNote(noteDto: NoteDto) {
        val id = noteDto.id ?: firestore.collection("notes").document().id
        val noteWithId = noteDto.copy(id = id)
        notesCollection.document(id).set(noteWithId).await()
    }

    suspend fun getNotesByJournalId(journalId: String): List<NoteDto> {
        return try {
            notesCollection
                .whereEqualTo("journalId", journalId)
                .get()
                .await()
                .toObjects(NoteDto::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteNote(noteId: String) {
        notesCollection.document(noteId).delete().await()
    }
}