
package com.hariku.feature_article.data.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hariku.feature_article.data.remote.dto.ArticleDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ArticleRemoteDataSource(
    firestore: FirebaseFirestore
) {
    private val articlesCollection = firestore.collection("articles")
    
    fun observeArticles(): Flow<List<ArticleDto>> = callbackFlow {
        val listener = articlesCollection
            .orderBy("uploadedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val articles = snapshot?.toObjects(ArticleDto::class.java) ?: emptyList()
                trySend(articles)
            }
        
        awaitClose { listener.remove() }
    }
    
    suspend fun getArticleById(id: String): ArticleDto? {
        return try {
            val snapshot = articlesCollection
                .whereEqualTo("id", id)
                .limit(1)
                .get()
                .await()
            
            if (snapshot.documents.isNotEmpty()) {
                val article = snapshot.documents.first().toObject(ArticleDto::class.java)
                article
            } else {
                Log.w("ArticleRemoteDataSource", "No article found with id: $id")
                null
            }
        } catch (e: Exception) {
            Log.e("ArticleRemoteDataSource", "Error fetching article by id: $id", e)
            null
        }
    }
    
    suspend fun getArticlesByCategory(category: String): List<ArticleDto> {
        return try {
            val snapshot = articlesCollection
                .whereEqualTo("category", category)
                .orderBy("uploadedAt", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(ArticleDto::class.java)
        } catch (e: Exception) {
            Log.e("ArticleRemoteDataSource", "Error fetching articles by category: $category", e)
            emptyList()
        }
    }
}