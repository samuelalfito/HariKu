package com.hariku.feature_journal.data.mapper

import com.hariku.feature_journal.data.dto.NoteDto
import com.hariku.feature_journal.data.local.entity.NoteEntity
import com.hariku.feature_journal.domain.model.Note

class NoteMapper {
    fun toDomain(dto: NoteDto): Note {
        return Note(
            id = dto.id.orEmpty(),
            journalId = dto.journalId.orEmpty(),
            userId = dto.userId.orEmpty(),
            title = dto.title.orEmpty(),
            content = dto.content.orEmpty(),
            imageUrls = dto.imageUrls ?: emptyList(),
            dateTime = dto.dateTime ?: 0L,
            createdAt = dto.createdAt ?: System.currentTimeMillis()
        )
    }

    fun toDto(model: Note): NoteDto {
        return NoteDto(
            id = model.id,
            journalId = model.journalId,
            userId = model.userId,
            title = model.title,
            content = model.content,
            imageUrls = model.imageUrls,
            dateTime = model.dateTime,
            createdAt = model.createdAt
        )
    }

    fun toEntity(model: Note): NoteEntity {
        return NoteEntity(
            id = model.id,
            journalId = model.journalId,
            userId = model.userId,
            title = model.title,
            content = model.content,
            imageUrls = model.imageUrls.joinToString(","),
            dateTime = model.dateTime,
            createdAt = model.createdAt
        )
    }

    fun fromEntity(entity: NoteEntity): Note {
        return Note(
            id = entity.id,
            journalId = entity.journalId,
            userId = entity.userId,
            title = entity.title,
            content = entity.content,
            imageUrls = if (entity.imageUrls.isEmpty()) emptyList() else entity.imageUrls.split(","),
            dateTime = entity.dateTime,
            createdAt = entity.createdAt
        )
    }
}

