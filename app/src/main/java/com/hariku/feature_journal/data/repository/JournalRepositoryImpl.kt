package com.hariku.feature_journal.data.repository

import com.hariku.feature_journal.data.local.dao.JournalDao
import com.hariku.feature_journal.data.mapper.JournalMapper
import com.hariku.feature_journal.domain.model.JournalEntry
import com.hariku.feature_journal.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementasi Repository (Lapisan Data)
 * Bertanggung jawab untuk mengimplementasikan interface domain dan
 * mengelola berbagai sumber data (dalam kasus ini, hanya Room/Lokal).
 */
class JournalRepositoryImpl(
    private val dao: JournalDao,
    private val mapper: JournalMapper
) : JournalRepository {

    override fun getAllJournals(userId: String): Flow<List<JournalEntry>> {
        // Menggunakan Flow dari Room dan memetakannya ke Domain Model
        return dao.getAllJournalsByUserId(userId).map { fullEntities ->
            fullEntities.map(mapper::toDomain)
        }
    }

    override suspend fun getJournalById(journalId: Long): JournalEntry? {
        return dao.getJournalById(journalId)?.let(mapper::toDomain)
    }

    override suspend fun saveJournal(journal: JournalEntry): Long {
        // 1. Konversi Domain Model utama ke Entity
        val entryEntity = mapper.toEntryEntity(journal)

        // 2. Tentukan ID jurnal (penting untuk Foreign Key)
        val parentId = if (journal.id > 0) journal.id else 0

        // 3. Konversi Element Domain ke Element Entity dengan parent ID yang benar
        val stickerEntities = journal.stickerElements.map { mapper.toStickerEntity(it, parentId) }
        val textEntities = journal.textElements.map { mapper.toTextEntity(it, parentId) }

        // 4. Lakukan operasi penyimpanan transaksional
        return dao.saveFullJournal(entryEntity, stickerEntities, textEntities)
    }

    override suspend fun deleteJournal(journalId: Long) {
        dao.deleteJournalById(journalId)
    }
}