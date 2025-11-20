package com.hariku.feature_chatbot.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.hariku.feature_chatbot.data.dto.ChatbotDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatbotFirebaseService(
    private val firestore: FirebaseFirestore
) {
    
    companion object {
        private const val COLLECTION_CHATBOTS = "chatbots"
        private const val SUBCOLLECTION_BOTS = "bots"
    }
    
    private fun getUserBotsCollection(userId: String) =
        firestore.collection(COLLECTION_CHATBOTS)
            .document(userId)
            .collection(SUBCOLLECTION_BOTS)
    
    fun getChatbots(userId: String): Flow<List<ChatbotDto>> = callbackFlow {
        val listener = getUserBotsCollection(userId)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val chatbots = snapshot?.toObjects(ChatbotDto::class.java) ?: emptyList()
                trySend(chatbots)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun addChatbot(chatbot: ChatbotDto, userId: String) {
        getUserBotsCollection(userId)
            .document(chatbot.id ?: "")
            .set(chatbot)
            .await()
    }
    
    suspend fun deleteChatbot(chatbotId: String, userId: String) {
        getUserBotsCollection(userId)
            .document(chatbotId)
            .delete()
            .await()
    }
    
    suspend fun getChatbotById(chatbotId: String, userId: String): ChatbotDto? {
        return getUserBotsCollection(userId)
            .document(chatbotId)
            .get()
            .await()
            .toObject(ChatbotDto::class.java)
    }
}