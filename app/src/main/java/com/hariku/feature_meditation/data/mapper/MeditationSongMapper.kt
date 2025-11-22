package com.hariku.feature_meditation.data.mapper

import com.hariku.feature_meditation.data.dto.MeditationSongDto
import com.hariku.feature_meditation.data.local.MeditationSongEntity
import com.hariku.feature_meditation.domain.model.MeditationSongModel

object MeditationSongMapper {
    
    fun toDomain(dto: MeditationSongDto): MeditationSongModel {
        return MeditationSongModel(
            id = dto.id.orEmpty(),
            title = dto.title.orEmpty(),
            category = dto.category.orEmpty(),
            imageResName = dto.imageResName.orEmpty(),
            audioResName = dto.audioResName.orEmpty(),
            durationMs = dto.durationMs ?: 0L,
            description = dto.description.orEmpty()
        )
    }
    
    fun toEntity(model: MeditationSongModel): MeditationSongEntity {
        return MeditationSongEntity(
            id = model.id,
            title = model.title,
            category = model.category,
            imageResName = model.imageResName,
            audioResName = model.audioResName,
            durationMs = model.durationMs,
            description = model.description
        )
    }
    
    fun fromEntity(entity: MeditationSongEntity): MeditationSongModel {
        return MeditationSongModel(
            id = entity.id,
            title = entity.title,
            category = entity.category,
            imageResName = entity.imageResName,
            audioResName = entity.audioResName,
            durationMs = entity.durationMs,
            description = entity.description
        )
    }
    
    fun toDomainList(dtos: List<MeditationSongDto>): List<MeditationSongModel> {
        return dtos.map { toDomain(it) }
    }
    
    fun fromEntityList(entities: List<MeditationSongEntity>): List<MeditationSongModel> {
        return entities.map { fromEntity(it) }
    }
    
    fun toEntityList(models: List<MeditationSongModel>): List<MeditationSongEntity> {
        return models.map { toEntity(it) }
    }
}