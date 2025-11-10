package com.hariku.feature_home.data.mapper

import com.hariku.feature_home.data.dto.MoodDto
import com.hariku.feature_home.data.local.MoodEntity
import com.hariku.feature_home.domain.model.MoodModel

object MoodMapper {
    fun toDomain(dto: MoodDto): MoodModel {
        return MoodModel(
            id = dto.id.orEmpty(),
            userId = dto.userId.orEmpty(),
            moodType = dto.moodType.orEmpty(),
            date = dto.date.orEmpty(),
            timestamp = dto.timestamp ?: 0L
        )
    }

    fun fromDomain(model: MoodModel): MoodDto {
        return MoodDto(
            id = model.id,
            userId = model.userId,
            moodType = model.moodType,
            date = model.date,
            timestamp = model.timestamp
        )
    }

    fun fromEntity(entity: MoodEntity): MoodModel {
        return MoodModel(
            id = entity.id,
            userId = entity.userId,
            moodType = entity.moodType,
            date = entity.date,
            timestamp = entity.timestamp
        )
    }

    fun toEntity(model: MoodModel): MoodEntity {
        return MoodEntity(
            id = model.id,
            userId = model.userId,
            moodType = model.moodType,
            date = model.date,
            timestamp = model.timestamp
        )
    }
}

