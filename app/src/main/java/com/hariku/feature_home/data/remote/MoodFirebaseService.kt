package com.hariku.feature_home.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hariku.feature_home.data.dto.MoodDto
import kotlinx.coroutines.tasks.await

class MoodFirebaseService(
    private val firestore: FirebaseFirestore
) {
    private val moodsCollection = "moods"

    suspend fun saveMood(userId: String, mood: MoodDto): Result<Unit> {
        return try {
            val moodMap = mapOf(
                "id" to mood.id,
                "userId" to mood.userId,
                "moodType" to mood.moodType,
                "date" to mood.date,
                "timestamp" to mood.timestamp
            )
            
            firestore.collection(moodsCollection)
                .document(userId)
                .collection("userMoods")
                .document(mood.id ?: "")
                .set(moodMap)
                .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMoodByDate(userId: String, date: String): MoodDto? {
        return try {
            val snapshot = firestore.collection(moodsCollection)
                .document(userId)
                .collection("userMoods")
                .whereEqualTo("date", date)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            if (snapshot.documents.isNotEmpty()) {
                val doc = snapshot.documents[0]
                MoodDto(
                    id = doc.getString("id"),
                    userId = doc.getString("userId"),
                    moodType = doc.getString("moodType"),
                    date = doc.getString("date"),
                    timestamp = doc.getLong("timestamp")
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAllMoods(userId: String): List<MoodDto> {
        return try {
            val snapshot = firestore.collection(moodsCollection)
                .document(userId)
                .collection("userMoods")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                MoodDto(
                    id = doc.getString("id"),
                    userId = doc.getString("userId"),
                    moodType = doc.getString("moodType"),
                    date = doc.getString("date"),
                    timestamp = doc.getLong("timestamp")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}