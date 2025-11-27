package com.hariku.feature_journal.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.hariku.feature_journal.data.local.entity.JournalFullEntity
import com.hariku.feature_journal.data.local.entity.JournalEntryEntity
import com.hariku.feature_journal.data.local.entity.StickerElementEntity
import com.hariku.feature_journal.data.local.entity.TextElementEntity
import com.hariku.feature_journal.domain.model.JournalEntry
import com.hariku.feature_journal.domain.model.StickerElement
import com.hariku.feature_journal.domain.model.TextElement

/**
 * Kelas Mapper: Bertanggung jawab untuk konversi antara Domain Model dan Data Entity.
 */
class JournalMapper {

    // --- Konversi Domain -> Entity ---

    fun toEntryEntity(domain: JournalEntry): JournalEntryEntity {
        return JournalEntryEntity(
            journalId = domain.id,
            userId = domain.userId,
            title = domain.title,
            bgRes = domain.bgRes,
            date = domain.date,
            description = domain.description
        )
    }

    fun toStickerEntity(domain: StickerElement, parentId: Long): StickerElementEntity {
        return StickerElementEntity(
            stickerId = domain.id,
            parentJournalId = parentId,
            emoji = domain.emoji,
            offsetX = domain.offsetX,
            offsetY = domain.offsetY,
            scale = domain.scale,
            rotation = domain.rotation
        )
    }

    fun toTextEntity(domain: TextElement, parentId: Long): TextElementEntity {
        return TextElementEntity(
            textId = domain.id,
            parentJournalId = parentId,
            text = domain.text,
            offsetX = domain.offsetX,
            offsetY = domain.offsetY,
            fontSize = domain.fontSize,
            // Konversi tipe data Compose ke tipe yang dapat disimpan (Long/String)
            colorLong = domain.color.value.toLong(),
            fontFamily = domain.fontFamily,
            // FIX: Menggunakan mapping 'when' untuk konversi
            textAlignString = when (domain.textAlign) {
                TextAlign.Start -> "Start"
                TextAlign.End -> "End"
                TextAlign.Center -> "Center"
                TextAlign.Justify -> "Justify"
                else -> "Start"
            },
            isUnderlined = domain.isUnderlined,
            shadowX = domain.shadowX,
            shadowY = domain.shadowY,
            shadowRadius = domain.shadowRadius,
            shadowColorLong = domain.shadowColor.value.toLong(),
            shadowOpacity = domain.shadowOpacity,
            outlineWidth = domain.outlineWidth,
            outlineColorLong = domain.outlineColor.value.toLong(),
            scale = domain.scale
        )
    }

    // --- Konversi Entity -> Domain ---

    fun toStickerDomain(entity: StickerElementEntity): StickerElement {
        return StickerElement(
            id = entity.stickerId,
            emoji = entity.emoji,
            offsetX = entity.offsetX,
            offsetY = entity.offsetY,
            scale = entity.scale,
            rotation = entity.rotation
        )
    }

    fun toTextDomain(entity: TextElementEntity): TextElement {
        return TextElement(
            id = entity.textId,
            text = entity.text,
            offsetX = entity.offsetX,
            offsetY = entity.offsetY,
            fontSize = entity.fontSize,
            // Konversi kembali dari tipe yang disimpan ke tipe Compose
            color = Color(entity.colorLong),
            fontFamily = entity.fontFamily,
            // FIX: Menggunakan mapping 'when' untuk konversi
            textAlign = when (entity.textAlignString) {
                "End" -> TextAlign.End
                "Center" -> TextAlign.Center
                "Justify" -> TextAlign.Justify
                "Start" -> TextAlign.Start
                else -> TextAlign.Start
            },
            isUnderlined = entity.isUnderlined,
            shadowX = entity.shadowX,
            shadowY = entity.shadowY,
            shadowRadius = entity.shadowRadius,
            shadowColor = Color(entity.shadowColorLong),
            shadowOpacity = entity.shadowOpacity,
            outlineWidth = entity.outlineWidth,
            outlineColor = Color(entity.outlineColorLong),
            scale = entity.scale
        )
    }

    fun toDomain(fullEntity: JournalFullEntity): JournalEntry {
        val entry = fullEntity.entry
        return JournalEntry(
            id = entry.journalId,
            userId = entry.userId,
            title = entry.title,
            bgRes = entry.bgRes,
            date = entry.date,
            description = entry.description,
            stickerElements = fullEntity.stickerElements.map(::toStickerDomain),
            textElements = fullEntity.textElements.map(::toTextDomain)
        )
    }
}