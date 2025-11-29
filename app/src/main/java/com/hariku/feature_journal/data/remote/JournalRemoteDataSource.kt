package com.hariku.feature_journal.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_journal.data.mapper.JournalMapper
import com.hariku.feature_journal.domain.model.Journal
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class JournalRemoteDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    val tag = "JournalRemoteDataSource: "

    private fun getJournalsSubcollection() = firestore
        .collection("Users")
        .document(getCurrentUser()?.uid ?: throw IllegalStateException("User not logged in"))
        .collection("Journals")

    private fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // --- Fungsi CRUD ---

    /**
     * Mengambil semua dokumen Journal dari subkoleksi pengguna yang sedang login.
     * @return List<Journal>
     */
    suspend fun getAllJournals(): List<Journal> {
        val snapshot = getJournalsSubcollection() // Akses subkoleksi journals pengguna saat ini
            .get()
            .await()

        // Mapping hasil snapshot ke List<Journal> menggunakan JournalMapper
        return snapshot.documents.mapNotNull { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                // Gunakan ID dokumen Firestore dan data Map untuk membuat objek Journal
                JournalMapper.fromFirestoreJournal(documentSnapshot.id, data)
            } else {
                null
            }
        }
    }

    fun getAllJournalsFlow(): Flow<List<Journal>> = callbackFlow {

        // 1. Dapatkan referensi ke subkoleksi Journals
        val collectionRef = getJournalsSubcollection()

        // 2. Tambahkan Snapshot Listener
        // Listener ini akan dipanggil pertama kali dan setiap kali ada perubahan
        val subscription = collectionRef
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Jika ada error, kirim error ke Flow
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    // Mapping data dari QuerySnapshot ke List<Journal>
                    val journals = snapshot.documents.mapNotNull { documentSnapshot ->
                        val data = documentSnapshot.data
                        if (data != null) {
                            JournalMapper.fromFirestoreJournal(documentSnapshot.id, data)
                        } else {
                            null
                        }
                    }
                    // Kirim hasil List<Journal> ke Flow
                    trySend(journals)
                }
            }

        // 3. Hentikan listener ketika Flow berhenti dikumpulkan (dibatalkan)
        awaitClose {
            subscription.remove()
        }
    }

    suspend fun saveJournal(journal: Journal) {
        val journalMap = JournalMapper.toFirestoreMap(journal)

        // Akses Subkoleksi untuk menyimpan dokumen
        getJournalsSubcollection()
            .document(journal.id)
            .set(journalMap)
            .await()
    }

    suspend fun deleteJournal(journalId: String) {
        // Akses Subkoleksi untuk menghapus dokumen
        getJournalsSubcollection()
            .document(journalId)
            .delete()
            .await()
    }

    suspend fun getJournalsByUserId(userId: String): List<Journal> {
        val subcollectionRef = firestore
            .collection("Users")
            .document(userId)
            .collection("Journals")

        val snapshot = subcollectionRef
            .get()
            .await()

        return snapshot.documents.mapNotNull { documentSnapshot ->
            val data = documentSnapshot.data
            if (data != null) {
                JournalMapper.fromFirestoreJournal(documentSnapshot.id, data)
            } else {
                null
            }
        }
    }
}