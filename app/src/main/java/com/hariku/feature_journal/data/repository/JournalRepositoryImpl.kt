package com.hariku.feature_journal.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_journal.data.remote.JournalRemoteDataSource
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class JournalRepositoryImpl(
    private val remoteDataSource: JournalRemoteDataSource
) : JournalRepository {

    override suspend fun saveJournal(journal: Journal) {
        val journalToSave = if (journal.id.isBlank()) {
            // Kalo ID kosong (journal baru), buat UID.
            val newId = UUID.randomUUID().toString()
            journal.copy(id = newId)
        } else {
            journal
        }

        remoteDataSource.saveJournal(journalToSave)
    }

    override suspend fun deleteJournal(journalId: String) {
        remoteDataSource.deleteJournal(journalId)
    }

    override fun getAllJournals(): Flow<List<Journal>> {
        // 🔥 Panggil fungsi Flow dari Data Source
        return remoteDataSource.getAllJournalsFlow()
    }

    override suspend fun getJournalsByUserId(userId: String): List<Journal> {
        return remoteDataSource.getJournalsByUserId(userId)
    }
}