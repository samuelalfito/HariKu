package com.hariku.feature_auth.data.dto

/**
 * DTO (Data Transfer Object) untuk User dari Firestore.
 * Nullable karena data dari network/database bisa saja tidak lengkap.
 */
data class UserDto(
    val uid: String? = null,
    val email: String? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val createdAt: Long? = null
)

