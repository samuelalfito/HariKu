package com.hariku.feature_journal.data.mapper

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hariku.feature_journal.domain.model.Journal

object JournalMapper {

    val tag = "JournalMapper: "

    fun fromFirestoreJournal(documentId: String, data: Map<String, Any?>): Journal {
        val textElementsList = (data["textElements"] as? List<Map<String, Any?>>)?.map {
            TextElementMapper.fromFirestoreMap(it)
        } ?: emptyList()

        val stickerElementsList = (data["stickerElements"] as? List<Map<String, Any?>>)?.map {
            StickerElementMapper.fromFirestoreMap(it)
        } ?: emptyList()

        Log.d(tag, "Loaded journal: ${textElementsList[0].text} ${textElementsList[0].offsetY}")

        return Journal(
            id = documentId, //Id nya dari nama dokumen
            userId = data["userId"] as String,
            title = data["title"] as String,
            date = data["date"] as String,
            backgroundColor = Color((data["backgroundColor"] as Long).toInt()), // GANTI
//            backgroundColor = (data["backgroundColor"] as String).toComposeColor(),
            textElements = textElementsList,
            stickerElements = stickerElementsList
        )
    }

    fun toFirestoreMap(journal: Journal): Map<String, Any?>{
        try {
            return hashMapOf(
                "userId" to journal.userId,
                "title" to journal.title,
                "date" to journal.date,
                "backgroundColor" to journal.backgroundColor.toArgb(), // GANTI
//                "backgroundColor" to journal.backgroundColor.toArgbHexString(),
                "textElements" to journal.textElements.map { TextElementMapper.toFirestoreMap(it) },
                "stickerElements" to journal.stickerElements.map { StickerElementMapper.toFirestoreMap(it) }
            )
        } catch (e: Exception){
            Log.e(tag, "Error converting Journal to Firestore Map: ${e.message}")
            throw e
        }
    }

    fun Color.toArgbHexString(): String {
        // Mengubah nilai Long ke String heksa dan memastikan 8 digit (AARRGGBB)
        return "#${this.value.toString(16).padStart(8, '0')}"
    }

    fun String.toComposeColor(): Color {
        // Menghapus '#' dan mengonversi String heksa ke Long (radix 16)
        val colorValue = this.removePrefix("#").toLong(16)
        return Color(colorValue)
    }
}