package com.hariku.feature_chatbot.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.hariku.feature_chatbot.domain.model.Chatbot

class GeminiApiService(
    private val apiKey: String
) {
    companion object {
        private const val MODEL_NAME = "gemini-2.0-flash-lite"
    }
    
    suspend fun generateResponse(
        chatbot: Chatbot,
        userMessage: String,
        conversationHistory: List<Pair<String, Boolean>> = emptyList()
    ): String {
        if (apiKey.isBlank() || apiKey == "YOUR_GEMINI_API_KEY_HERE") {
            return """
                ⚠️ Gemini API Key belum diatur!
                
                Untuk menggunakan AI chatbot:
                1. Dapatkan API key dari: https://makersuite.google.com/app/apikey
                2. Buka file local.properties
                3. Set: gemini.api.key=YOUR_ACTUAL_KEY
                4. Sync gradle dan restart app
            """.trimIndent()
        }
        
        return try {
            val model = GenerativeModel(
                modelName = MODEL_NAME,
                apiKey = apiKey
            )
            
            val systemInstruction = buildSystemInstruction(chatbot)
            val conversationContext = buildConversationContext(conversationHistory)
            
            val prompt = """
                $systemInstruction
                
                $conversationContext
                
                User: $userMessage
                ${chatbot.name}:
            """.trimIndent()
            
            val response = model.generateContent(prompt)
            response.text ?: "Maaf, saya tidak bisa merespons saat ini."
        } catch (e: Exception) {
            "Maaf, terjadi kesalahan: ${e.message}"
        }
    }
    
    private fun buildSystemInstruction(chatbot: Chatbot): String {
        return """
            Kamu adalah ${chatbot.name}, sebuah chatbot dengan kepribadian sebagai berikut:
            
            Kepribadian Umum: ${chatbot.subtitle}
            
            Detail Kepribadian:
            ${chatbot.description}
            
            ATURAN PENTING:
            1. Selalu respons sesuai dengan kepribadian yang telah ditentukan
            2. Gunakan nama ${chatbot.name} saat memperkenalkan diri
            3. Jaga konsistensi karakter sepanjang percakapan
            4. Berikan respons yang empatik dan membantu
            5. Gunakan bahasa Indonesia yang natural dan ramah
            6. Pecah respons menjadi paragraf jika diperlukan untuk readability
            7. Jangan pernah keluar dari karakter
            8. Jangan gunakan format markdown atau special characters
            9. Berikan respons yang hangat dan supportif
            10. Peran anda adalah menjadi teman ngobrol yang menyenangkan dan informatif untuk orang dengan mental down
            11. Selalu ingat kembali percakapan sebelumnya untuk menjaga konteks (beda karakter dengan chatbot lain)
        """.trimIndent()
    }
    
    private fun buildConversationContext(history: List<Pair<String, Boolean>>): String {
        if (history.isEmpty()) return "Ini adalah percakapan baru."
        
        val context = StringBuilder("Konteks Percakapan Sebelumnya:\n")
        history.takeLast(10).forEach { (message, isFromUser) ->
            val speaker = if (isFromUser) "User" else "Bot"
            context.append("$speaker: $message\n")
        }
        return context.toString()
    }
}


