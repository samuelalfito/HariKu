package com.hariku.feature_journal.data.mapper

import com.hariku.feature_journal.data.local.dao.PromptCacheEntity
import com.hariku.feature_journal.domain.model.PromptResponse
import com.hariku.feature_journal.domain.model.PromptSuggestion
import org.json.JSONArray
import org.json.JSONObject

object PromptMapper {
    
    fun toCacheEntity(response: PromptResponse): PromptCacheEntity {
        val suggestionsJson = JSONArray().apply {
            response.suggestions.forEach { suggestion ->
                put(JSONObject().apply {
                    put("id", suggestion.id)
                    put("text", suggestion.text)
                    put("isSelected", suggestion.isSelected)
                })
            }
        }.toString()
        
        return PromptCacheEntity(
            introduction = response.introduction,
            suggestionsJson = suggestionsJson
        )
    }
    
    fun fromCacheEntity(entity: PromptCacheEntity): PromptResponse {
        val suggestionsArray = JSONArray(entity.suggestionsJson)
        val suggestions = mutableListOf<PromptSuggestion>()
        
        for (i in 0 until suggestionsArray.length()) {
            val json = suggestionsArray.getJSONObject(i)
            suggestions.add(
                PromptSuggestion(
                    id = json.getString("id"),
                    text = json.getString("text"),
                    isSelected = json.optBoolean("isSelected", false)
                )
            )
        }
        
        return PromptResponse(
            introduction = entity.introduction,
            suggestions = suggestions
        )
    }
}