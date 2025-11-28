package com.hariku.feature_journal.data.mapper

import com.hariku.feature_journal.data.dto.JournalOverviewDto
import com.hariku.feature_journal.data.local.entity.JournalOverviewEntity
import com.hariku.feature_journal.domain.model.JournalOverview

class JournalOverviewMapper {
    fun toDomain(dto: JournalOverviewDto): JournalOverview {
        return JournalOverview(
            journalId = dto.journalId.orEmpty(),
            overview = dto.overview.orEmpty(),
            noteCount = dto.noteCount ?: 0,
            lastUpdated = dto.lastUpdated ?: 0L,
            generatedAt = dto.generatedAt ?: System.currentTimeMillis()
        )
    }

    fun toDto(model: JournalOverview): JournalOverviewDto {
        return JournalOverviewDto(
            journalId = model.journalId,
            overview = model.overview,
            noteCount = model.noteCount,
            lastUpdated = model.lastUpdated,
            generatedAt = model.generatedAt
        )
    }

    fun toEntity(model: JournalOverview): JournalOverviewEntity {
        return JournalOverviewEntity(
            journalId = model.journalId,
            overview = model.overview,
            noteCount = model.noteCount,
            lastUpdated = model.lastUpdated,
            generatedAt = model.generatedAt
        )
    }

    fun fromEntity(entity: JournalOverviewEntity): JournalOverview {
        return JournalOverview(
            journalId = entity.journalId,
            overview = entity.overview,
            noteCount = entity.noteCount,
            lastUpdated = entity.lastUpdated,
            generatedAt = entity.generatedAt
        )
    }
}

