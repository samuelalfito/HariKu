package com.hariku.feature_auth.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.hariku.feature_auth.data.dto.UserDto
import com.hariku.feature_auth.domain.model.AuthUser

object UserMapper {
    
    /**
     * Konversi dari FirebaseUser (Firebase SDK) ke AuthUser (Domain Model)
     */
    fun fromFirebaseUser(firebaseUser: FirebaseUser, name: String? = null): AuthUser {
        return AuthUser(
            uid = firebaseUser.uid,
            email = firebaseUser.email,
            name = name ?: firebaseUser.displayName,
            photoUrl = firebaseUser.photoUrl?.toString()
        )
    }
    
    /**
     * Konversi dari UserDto (Firestore) ke AuthUser (Domain Model)
     */
    fun fromDto(dto: UserDto): AuthUser {
        return AuthUser(
            uid = dto.uid.orEmpty(),
            email = dto.email,
            name = dto.name,
            photoUrl = dto.photoUrl
        )
    }
    
    /**
     * Konversi dari AuthUser (Domain Model) ke UserDto (Firestore)
     */
    fun toDto(authUser: AuthUser): UserDto {
        return UserDto(
            uid = authUser.uid,
            email = authUser.email,
            name = authUser.name,
            photoUrl = authUser.photoUrl
        )
    }
    
    /**
     * Konversi dari AuthUser ke Map untuk Firestore
     */
    fun toFirestoreMap(authUser: AuthUser): Map<String, Any?> {
        return hashMapOf(
            "uid" to authUser.uid,
            "email" to authUser.email,
            "name" to authUser.name,
            "photoUrl" to authUser.photoUrl,
            "createdAt" to System.currentTimeMillis()
        )
    }
}

