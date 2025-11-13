package com.hariku.feature_auth.data.repository

import com.google.firebase.auth.FirebaseUser
import com.hariku.feature_auth.data.mapper.UserMapper
import com.hariku.feature_auth.data.remote.AuthRemoteDataSource
import com.hariku.feature_auth.domain.model.AuthUser
import com.hariku.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

/**
 * Implementasi Repository untuk Authentication.
 * - Menggunakan Remote Data Source untuk akses data
 * - Menggunakan Mapper untuk konversi DTO ↔ Domain Model
 * - Tidak bergantung langsung pada Firebase (loose coupling)
 */
class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            val firebaseUser = remoteDataSource.login(email, password)
            
            val userDto = remoteDataSource.getUserFromFirestore(firebaseUser.uid)
            
            val authUser = if (userDto != null) {
                UserMapper.fromDto(userDto)
            } else {
                UserMapper.fromFirebaseUser(firebaseUser)
            }
            
            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<AuthUser> {
        return try {
            val firebaseUser = remoteDataSource.loginWithGoogle(idToken)

            val authUser = AuthUser(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                name = firebaseUser.displayName
            )

            val userMap = UserMapper.toFirestoreMap(authUser)
            remoteDataSource.saveUserToFirestore(firebaseUser.uid, userMap)

            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String, name: String): Result<AuthUser> {
        return try {
            val firebaseUser = remoteDataSource.signUp(email, password, name)
            
            val authUser = AuthUser(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                name = name
            )
            
            val userMap = UserMapper.toFirestoreMap(authUser)
            remoteDataSource.saveUserToFirestore(firebaseUser.uid, userMap)
            
            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): AuthUser? {
        val firebaseUser = remoteDataSource.getCurrentFirebaseUser() ?: return null
        
        return UserMapper.fromFirebaseUser(firebaseUser)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAuthState(): Flow<AuthUser?> {
        return remoteDataSource.getAuthStateFlow()
            .flatMapLatest { firebaseUser ->

                if (firebaseUser == null) {
                    flowOf(null)
                } else {
                    flowOf(fetchUserFromFirestoreOrFallback(firebaseUser))
                }
            }
    }

    override fun logout() {
        remoteDataSource.logout()
    }

    private suspend fun fetchUserFromFirestoreOrFallback(firebaseUser: FirebaseUser): AuthUser {
        val userDto = remoteDataSource.getUserFromFirestore(firebaseUser.uid)
        return if (userDto != null) {
            UserMapper.fromDto(userDto)
        } else {
            UserMapper.fromFirebaseUser(firebaseUser)
        }
    }
}