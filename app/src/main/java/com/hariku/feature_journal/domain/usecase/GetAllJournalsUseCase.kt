package com.hariku.feature_journal.domain.usecase

import com.hariku.feature_journal.domain.model.Journal
import com.hariku.feature_journal.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllJournalsUseCase(
    private val repository: JournalRepository
) {
    /**
     * Mengambil semua Journal milik pengguna saat ini sebagai Flow real-time.
     * @return Flow<List<Journal>>
     */
    operator fun invoke(): Flow<List<Journal>> {
        return repository.getAllJournals()
            .map { journals ->
                // Logika bisnis: Mengurutkan berdasarkan tanggal
                journals.sortedByDescending { it.date }
            }
    }
}