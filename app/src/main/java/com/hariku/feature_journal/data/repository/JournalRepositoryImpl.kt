package com.hariku.feature_journal.data.repository

import com.hariku.feature_journal.data.remote.JournalRemoteDataSource
import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.repository.JournalRepository

class JournalRepositoryImpl(
    private val remoteDataSource: JournalRemoteDataSource
) : JournalRepository {

    override suspend fun saveJournal(journal: Journal) {
        remoteDataSource.saveJournal(journal)
    }

    override suspend fun deleteJournal(journalId: String) {
        remoteDataSource.deleteJournal(journalId)
    }

    override suspend fun getAllJournals(): List<Journal> {
        return remoteDataSource.getAllJournals()
    }

    override suspend fun getJournalsByUserId(userId: String): List<Journal> {
        return remoteDataSource.getJournalsByUserId(userId)
    }
}