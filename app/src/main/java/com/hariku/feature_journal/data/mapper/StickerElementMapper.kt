package com.hariku.feature_journal.data.mapper

import com.hariku.feature_journal.domain.model.StickerElement

object StickerElementMapper {
    fun toFirestoreMap(stickerElement: StickerElement): Map<String, Any?>{
        return hashMapOf(
            "id" to stickerElement.id,
            "emoji" to stickerElement.emoji,
            "offsetX" to stickerElement.offsetX,
            "offsetY" to stickerElement.offsetY,
            "scale" to stickerElement.scale,
            "rotation" to stickerElement.rotation
        )
    }

    fun fromFirestoreMap(data: Map<String, Any?>): StickerElement{
        return StickerElement(
            id = data["id"] as Long,
            emoji = data["emoji"] as String,
            offsetX = (data["offsetX"] as Double).toFloat(),
            offsetY = (data["offsetY"] as Double).toFloat(),
            scale = (data["scale"] as Double).toFloat(),
            rotation = (data["rotation"] as Double).toFloat()
        )
    }
}