package com.hariku.feature_journal.data.remote

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.hariku.feature_journal.domain.model.PromptRequest
import com.hariku.feature_journal.domain.model.PromptResponse
import com.hariku.feature_journal.domain.model.PromptSuggestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.UUID

class PromptAIService(
    private val generativeModel: GenerativeModel
) {
    companion object {
        private const val TAG = "PromptAIService"
    }
    
    suspend fun generatePrompts(request: PromptRequest): PromptResponse {
        return withContext(Dispatchers.IO) {
            try {
                val feelingsText = buildString {
                    append("Perasaan yang dipilih: ")
                    append(request.selectedFeelings.joinToString(", "))
                    if (request.customFeeling.isNotBlank()) {
                        append(". Perasaan tambahan: ${request.customFeeling}")
                    }
                }
                
                Log.d(TAG, "Generating prompts for: $feelingsText")
                
                val systemPrompt = """
                    Kamu adalah asisten AI yang membantu orang membuat jurnal tentang kesehatan mental mereka.
                    Berdasarkan perasaan user, buatkan:
                    1. Satu kalimat pengantar yang empati dan supportif
                    2. 6 prompt/pertanyaan untuk jurnal yang relevan dengan perasaan mereka
                    
                    Format response dalam JSON:
                    {
                        "introduction": "kalimat pengantar",
                        "suggestions": [
                            "prompt 1",
                            "prompt 2",
                            "prompt 3",
                            "prompt 4",
                            "prompt 5",
                            "prompt 6"
                        ]
                    }
                    
                    User input: $feelingsText
                    
                    Berikan response dalam bahasa Indonesia yang ramah dan mendukung.
                """.trimIndent()
                
                val response = generativeModel.generateContent(systemPrompt)
                val responseText = response.text
                
                if (responseText == null) {
                    Log.e(TAG, "Gemini returned null response")
                    throw Exception("No response from AI")
                }
                
                val parsed = parseAIResponse(responseText)
                Log.d(TAG, "Successfully generated ${parsed.suggestions.size} AI prompts")
                parsed
                
            } catch (e: Exception) {
                Log.e(TAG, "AI generation failed: ${e.message}")
                generateDefaultPrompts()
            }
        }
    }
    
    private fun parseAIResponse(responseText: String): PromptResponse {
        try {
            val jsonText = responseText
                .replace("```json", "")
                .replace("```", "")
                .trim()
            
            val json = JSONObject(jsonText)
            val introduction = json.getString("introduction")
            val suggestionsArray = json.getJSONArray("suggestions")
            
            val suggestions = mutableListOf<PromptSuggestion>()
            for (i in 0 until suggestionsArray.length()) {
                suggestions.add(
                    PromptSuggestion(
                        id = UUID.randomUUID().toString(),
                        text = suggestionsArray.getString(i)
                    )
                )
            }
            
            return PromptResponse(
                introduction = introduction,
                suggestions = suggestions
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse AI response: ${e.message}")
            throw Exception("Failed to parse AI response: ${e.message}")
        }
    }
    
    private fun generateDefaultPrompts(): PromptResponse {
        val defaultSuggestions = listOf(
            "Apa yang telah membantu kecemasanmu di masa lalu?",
            "Apa pemicu dari kecemasanmu? Kapan pertama terjadi?",
            "Kecemasan tidak menghambatku. Aku kuat karena...",
            "Ingatanku yang paling bahagia",
            "Apa yang membuatmu cemas?",
            "Apakah kecemasanku realistis?"
        ).map {
            PromptSuggestion(
                id = UUID.randomUUID().toString(),
                text = it
            )
        }
        
        return PromptResponse(
            introduction = "Berdasarkan yang sedang kamu alami saat ini, ini beberapa topik jurnal yang bisa kamu pakai. Pilih salah satu prompt untuk journalmu!",
            suggestions = defaultSuggestions
        )
    }
}