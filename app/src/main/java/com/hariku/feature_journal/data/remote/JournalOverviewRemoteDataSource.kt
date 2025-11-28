package com.hariku.feature_journal.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_journal.data.dto.JournalOverviewDto
import kotlinx.coroutines.tasks.await

class JournalOverviewRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val generativeModel: GenerativeModel
) {
    private val overviewsCollection = firestore.collection("journal_overviews")

    suspend fun generateOverview(notes: List<String>): String {
        val prompt = buildString {
            append("Buatlah ringkasan atau overview dari catatan-catatan jurnal berikut dalam bahasa Indonesia. ")
            append("Ringkasan harus mencakup tema utama, perasaan yang dominan, dan insight penting. ")
            append("Maksimal 3-4 kalimat.\n\n")
            append("Catatan-catatan:\n")
            notes.forEachIndexed { index, note ->
                append("${index + 1}. $note\n")
            }
        }

        val response = generativeModel.generateContent(prompt)
        return response.text ?: "Tidak dapat menghasilkan ringkasan."
    }

    suspend fun saveOverview(overviewDto: JournalOverviewDto) {
        val journalId = overviewDto.journalId ?: return
        overviewsCollection.document(journalId).set(overviewDto).await()
    }

    suspend fun getOverview(journalId: String): JournalOverviewDto? {
        return try {
            overviewsCollection.document(journalId)
                .get()
                .await()
                .toObject(JournalOverviewDto::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

