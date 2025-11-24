package com.hariku.feature_journal.data.mapper

import com.hariku.feature_journal.domain.model.Journal

object JournalMapper {
    fun fromFirestoreJournal(documentId: String, data: Map<String, Any?>): Journal {
        val textElementsList = (data["textElements"] as? List<Map<String, Any?>>)?.map {
            TextElementMapper.fromFirestoreMap(it)
        } ?: emptyList()

        val stickerElementsList = (data["stickerElements"] as? List<Map<String, Any?>>)?.map {
            StickerElementMapper.fromFirestoreMap(it)
        } ?: emptyList()

        return Journal(
            id = documentId, //Id nya dari nama dokumen
            userId = data["userId"] as String,
            title = data["title"] as String,
            date = data["date"] as String,
            textElements = textElementsList,
            stickerElements = stickerElementsList
        )
    }

    fun toFirestoreMap(journal: Journal): Map<String, Any?>{
        return hashMapOf(
            "userId" to journal.userId,
            "title" to journal.title,
            "date" to journal.date,
            "textElements" to journal.textElements.map { TextElementMapper.toFirestoreMap(it) },
            "stickerElements" to journal.stickerElements.map { StickerElementMapper.toFirestoreMap(it) }
        )
    }
}