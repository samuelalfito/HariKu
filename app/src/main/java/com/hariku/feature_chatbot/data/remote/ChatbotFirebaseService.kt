package com.hariku.feature_chatbot.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hariku.feature_chatbot.data.dto.ChatMessageDto
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
        private const val SUBCOLLECTION_MESSAGES = "messages"
    }
    
    private fun getUserBotsCollection(userId: String) =
        firestore.collection(COLLECTION_CHATBOTS)
            .document(userId)
            .collection(SUBCOLLECTION_BOTS)
    
    private fun getBotMessagesCollection(userId: String, botId: String) =
        getUserBotsCollection(userId)
            .document(botId)
            .collection(SUBCOLLECTION_MESSAGES)
    
    fun getChatbots(userId: String): Flow<List<ChatbotDto>> = callbackFlow {
        val listener = getUserBotsCollection(userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
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
    
    fun getChatMessages(chatbotId: String, userId: String): Flow<List<ChatMessageDto>> = callbackFlow {
        val listener = getBotMessagesCollection(userId, chatbotId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val messages = snapshot?.toObjects(ChatMessageDto::class.java) ?: emptyList()
                trySend(messages)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun getLastMessage(chatbotId: String, userId: String): ChatMessageDto? {
        val result = getBotMessagesCollection(userId, chatbotId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        
        return result.documents.firstOrNull()?.toObject(ChatMessageDto::class.java)
    }
    
    suspend fun getUnreadCount(chatbotId: String, userId: String): Int {
        val result = getBotMessagesCollection(userId, chatbotId)
            .whereEqualTo("isFromUser", false)
            .whereEqualTo("isRead", false)
            .get()
            .await()
        
        return result.size()
    }
    
    suspend fun getRecentMessages(chatbotId: String, userId: String, limit: Int = 10): List<ChatMessageDto> {
        val result = getBotMessagesCollection(userId, chatbotId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .get()
            .await()
        
        return result.toObjects(ChatMessageDto::class.java).reversed()
    }
    
    suspend fun sendMessage(message: ChatMessageDto, userId: String, chatbotId: String) {
        getBotMessagesCollection(userId, chatbotId)
            .document(message.id.ifEmpty { java.util.UUID.randomUUID().toString() })
            .set(message)
            .await()
    }
    
    suspend fun markMessagesAsRead(chatbotId: String, userId: String) {
        val unreadMessages = getBotMessagesCollection(userId, chatbotId)
            .whereEqualTo("isFromUser", false)
            .whereEqualTo("isRead", false)
            .get()
            .await()
        
        val batch = firestore.batch()
        unreadMessages.documents.forEach { doc ->
            batch.update(doc.reference, "isRead", true)
        }
        batch.commit().await()
    }
}