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
 * Mengikuti Clean Architecture:
 * - Menggunakan Remote Data Source untuk akses data
 * - Menggunakan Mapper untuk konversi DTO ↔ Domain Model
 * - Tidak bergantung langsung pada Firebase (loose coupling)
 */
class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        return try {
            // 1. Login melalui Remote Data Source
            val firebaseUser = remoteDataSource.login(email, password)
            
            // 2. Ambil data lengkap dari Firestore
            val userDto = remoteDataSource.getUserFromFirestore(firebaseUser.uid)
            
            // 3. Konversi ke Domain Model menggunakan Mapper
            val authUser = if (userDto != null) {
                UserMapper.fromDto(userDto)
            } else {
                // Fallback jika data tidak ada di Firestore
                UserMapper.fromFirebaseUser(firebaseUser)
            }
            
            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String, name: String): Result<AuthUser> {
        return try {
            // 1. Sign up melalui Remote Data Source
            val firebaseUser = remoteDataSource.signUp(email, password)
            
            // 2. Buat Domain Model
            val authUser = AuthUser(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                name = name
            )
            
            // 3. Konversi ke Map dan simpan ke Firestore
            val userMap = UserMapper.toFirestoreMap(authUser)
            remoteDataSource.saveUserToFirestore(firebaseUser.uid, userMap)
            
            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): AuthUser? {
        val firebaseUser = remoteDataSource.getCurrentFirebaseUser() ?: return null
        
        // Untuk getCurrentUser yang sinkron, kita kembalikan data dari FirebaseUser
        // Data lengkap dari Firestore bisa diambil secara async di tempat lain
        return UserMapper.fromFirebaseUser(firebaseUser)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAuthState(): Flow<AuthUser?> {
        // 1. Ambil Flow mentah dari data source
        return remoteDataSource.getAuthStateFlow()
            .flatMapLatest { firebaseUser ->
                // flatMapLatest hebat karena jika user log out lalu log in cepat,
                // ia akan membatalkan permintaan Firestore yang lama.

                if (firebaseUser == null) {
                    // 2. Jika user null (logout), kirim null ke UI
                    flowOf(null)
                } else {
                    // 3. Jika user login, kita ambil data LENGKAP dari Firestore
                    //    Sama seperti logika di fungsi login() Anda.
                    flowOf(fetchUserFromFirestoreOrFallback(firebaseUser))
                }
            }
    }

    override fun logout() {
        // Cukup teruskan panggilan ke data source
        remoteDataSource.logout()
    }

    /**
     * Fungsi helper pribadi untuk mengambil data dari Firestore
     * dengan fallback ke data Firebase Auth.
     */
    private suspend fun fetchUserFromFirestoreOrFallback(firebaseUser: FirebaseUser): AuthUser {
        val userDto = remoteDataSource.getUserFromFirestore(firebaseUser.uid)
        return if (userDto != null) {
            UserMapper.fromDto(userDto)
        } else {
            // Fallback jika Firestore belum siap/kosong
            UserMapper.fromFirebaseUser(firebaseUser)
        }
    }
}