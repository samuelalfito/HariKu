package com.hariku.feature_auth.domain.model

/**
 * Domain Model untuk User Authentication.
 * Model murni, tidak bergantung pada Firebase atau library eksternal.
 */
data class AuthUser(
    val uid: String,
    val email: String?,
    val name: String?
)

