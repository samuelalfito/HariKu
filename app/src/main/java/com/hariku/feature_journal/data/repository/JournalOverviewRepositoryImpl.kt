package com.hariku.feature_journal.data.repository

import com.hariku.feature_journal.data.local.dao.JournalOverviewDao
import com.hariku.feature_journal.data.mapper.JournalOverviewMapper
import com.hariku.feature_journal.data.remote.JournalOverviewRemoteDataSource
import com.hariku.feature_journal.domain.model.JournalOverview
import com.hariku.feature_journal.domain.repository.JournalOverviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalOverviewRepositoryImpl(
    private val remoteDataSource: JournalOverviewRemoteDataSource,
    private val dao: JournalOverviewDao,
    private val mapper: JournalOverviewMapper
) : JournalOverviewRepository {

    override suspend fun generateOverview(journalId: String, notes: List<String>): Result<JournalOverview> {
        return try {
            val overviewText = remoteDataSource.generateOverview(notes)
            
            val overview = JournalOverview(
                journalId = journalId,
                overview = overviewText,
                noteCount = notes.size,
                lastUpdated = System.currentTimeMillis(),
                generatedAt = System.currentTimeMillis()
            )
            
            // Save to Firestore
            val dto = mapper.toDto(overview)
            remoteDataSource.saveOverview(dto)
            
            // Save to local
            val entity = mapper.toEntity(overview)
            dao.insertOverview(entity)
            
            Result.success(overview)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOverview(journalId: String): JournalOverview? {
        return try {
            // Try remote first
            val remoteDto = remoteDataSource.getOverview(journalId)
            if (remoteDto != null) {
                val overview = mapper.toDomain(remoteDto)
                // Save to local cache
                dao.insertOverview(mapper.toEntity(overview))
                overview
            } else {
                // Fallback to local
                val localEntity = dao.getOverviewByJournalIdSync(journalId)
                localEntity?.let { mapper.fromEntity(it) }
            }
        } catch (e: Exception) {
            // Fallback to local on error
            val localEntity = dao.getOverviewByJournalIdSync(journalId)
            localEntity?.let { mapper.fromEntity(it) }
        }
    }

    override fun observeOverview(journalId: String): Flow<JournalOverview?> {
        return dao.getOverviewByJournalId(journalId).map { entity ->
            entity?.let { mapper.fromEntity(it) }
        }
    }

    override suspend fun saveOverview(overview: JournalOverview): Result<Unit> {
        return try {
            val dto = mapper.toDto(overview)
            remoteDataSource.saveOverview(dto)
            
            val entity = mapper.toEntity(overview)
            dao.insertOverview(entity)
            
            Result.success(Unit)
        } catch (e: Exception) {
            try {
                val entity = mapper.toEntity(overview)
                dao.insertOverview(entity)
                Result.success(Unit)
            } catch (localError: Exception) {
                Result.failure(localError)
            }
        }
    }
}

