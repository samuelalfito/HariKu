package com.hariku.feature_auth.domain.model

/**
 * Domain Model untuk User Authentication.
 * Ini adalah model murni yang tidak bergantung pada Firebase atau library eksternal apapun.
 */
data class AuthUser(
    val uid: String,
    val email: String?,
    val name: String?
)

