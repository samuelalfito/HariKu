package com.hariku.feature_journal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hariku.feature_journal.data.local.entity.JournalEntryEntity
import com.hariku.feature_journal.data.local.entity.JournalFullEntity
import com.hariku.feature_journal.data.local.entity.StickerElementEntity
import com.hariku.feature_journal.data.local.entity.TextElementEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    // --- Query SELECT ---

    /**
     * Mengambil semua jurnal untuk user tertentu, termasuk elemennya, secara real-time.
     */
    @Transaction
    @Query("SELECT * FROM journal_entries WHERE userId = :userId ORDER BY journalId DESC")
    fun getAllJournalsByUserId(userId: String): Flow<List<JournalFullEntity>>

    /**
     * Mengambil jurnal tunggal berdasarkan ID.
     */
    @Transaction
    @Query("SELECT * FROM journal_entries WHERE journalId = :journalId")
    suspend fun getJournalById(journalId: Long): JournalFullEntity?

    // --- Query INSERT/UPDATE ---

    /**
     * Menyimpan JournalEntryEntity. Jika id = 0, akan INSERT. Jika id > 0, akan UPDATE.
     * @return ID dari baris yang baru saja dimasukkan/diperbarui.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: JournalEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStickerElement(sticker: StickerElementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStickerElements(stickers: List<StickerElementEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextElement(text: TextElementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTextElements(texts: List<TextElementEntity>)

    // --- Query DELETE ---

    /**
     * Menghapus JournalEntryEntity dan secara otomatis menghapus semua Sticker/Text Element
     * terkait karena FOREIGN KEY dengan onDelete = CASCADE.
     */
    @Query("DELETE FROM journal_entries WHERE journalId = :journalId")
    suspend fun deleteJournalById(journalId: Long)

    /**
     * Menghapus semua elemen yang terkait dengan jurnal (untuk operasi UPDATE)
     * sebelum menyisipkan yang baru, karena elemen-elemen ini mungkin telah diubah/dihapus.
     */
    @Query("DELETE FROM sticker_elements WHERE parentJournalId = :journalId")
    suspend fun deleteStickersByJournalId(journalId: Long)

    @Query("DELETE FROM text_elements WHERE parentJournalId = :journalId")
    suspend fun deleteTextsByJournalId(journalId: Long)

    /**
     * Metode SAVE KOMPLEKS (transaksional)
     * Menyimpan seluruh JournalEntry, termasuk entri utama dan elemen-elemennya.
     */
    @Transaction
    suspend fun saveFullJournal(
        entry: JournalEntryEntity,
        stickers: List<StickerElementEntity>,
        texts: List<TextElementEntity>
    ): Long {
        val newJournalId = insertJournalEntry(entry)

        // Hapus elemen lama jika ini adalah UPDATE (journalId > 0)
        if (entry.journalId > 0) {
            deleteStickersByJournalId(entry.journalId)
            deleteTextsByJournalId(entry.journalId)
        }

        // Sisipkan elemen baru dengan ID jurnal yang benar (newJournalId)
        if (stickers.isNotEmpty()) {
            val stickersWithId = stickers.map { it.copy(parentJournalId = newJournalId) }
            insertAllStickerElements(stickersWithId)
        }
        if (texts.isNotEmpty()) {
            val textsWithId = texts.map { it.copy(parentJournalId = newJournalId) }
            insertAllTextElements(textsWithId)
        }

        return newJournalId
    }
}