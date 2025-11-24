package com.hariku.feature_journal.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.hariku.feature_journal.domain.model.TextElement

object TextElementMapper {
    fun toFirestoreMap(textElement: TextElement): Map<String, Any?>{
        return hashMapOf(
            "id" to textElement.id.toLong(),
            "text" to textElement.text,
            "offsetX" to textElement.offsetX,
            "offsetY" to textElement.offsetY,
            "fontSize" to textElement.fontSize,
            "color" to textElement.color.value.toLong(), // Ganti
            "fontFamily" to textElement.fontFamily,
            "textAlign" to textElement.textAlign.toString(), // Ganti, misalnya textAlign.Start.toString hasilnya "Start", nanti mapper lagi di remote datasource
            "isUnderlined" to textElement.isUnderlined,
            "shadowX" to textElement.shadowX,
            "shadowY" to textElement.shadowY,
            "shadowRadius" to textElement.shadowRadius,
            "shadowColor" to textElement.shadowColor.value.toLong(), // Ganti
            "shadowOpacity" to textElement.shadowOpacity,
            "outlineWidth" to textElement.outlineWidth,
            "outlineColor" to textElement.outlineColor.value.toLong(), // Ganti
            "scale" to textElement.scale
        )
    }

    fun fromFirestoreMap(data: Map<String, Any?>): TextElement {
        return TextElement(
            id = data["id"] as Long,
            text = data["text"] as String,
            offsetX = (data["offsetX"] as Double).toFloat(),
            offsetY = (data["offsetY"] as Double).toFloat(),
            fontSize = (data["fontSize"] as Double).toFloat(),
            color = Color(data["color"] as Long), // Ganti
            fontFamily = data["fontFamily"] as String,
            textAlign = stringToTextAlign(data["textAlign"] as String), // Ganti, misalnya TextAlign.Start
            isUnderlined = data["isUnderlined"] as Boolean,
            shadowX = (data["shadowX"] as Double).toFloat(),
            shadowY = (data["shadowX"] as Double).toFloat(),
            shadowRadius = (data["shadowRadius"] as Double).toFloat(),
            shadowColor = Color(data["shadowColor"] as Long), // Ganti
            shadowOpacity = (data["shadowOpacity"] as Double).toFloat(),
            outlineWidth = (data["outlineWidth"] as Double).toFloat(),
            outlineColor = Color(data["outlineColor"] as Long), // Ganti
            scale = (data["scale"] as Double).toFloat()
        )
    }

    fun stringToTextAlign(data: String): TextAlign{
        return when(data){
            "Left" -> TextAlign.Left
            "Right" -> TextAlign.Right
            "Center" -> TextAlign.Center
            "Justify" -> TextAlign.Justify
            "Start" -> TextAlign.Start
            "End" -> TextAlign.End
            else -> TextAlign.Start
        }
    }
}