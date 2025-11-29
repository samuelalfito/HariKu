package com.hariku.feature_auth.data.mapper

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * CONTOH KONKRET: DAMPAK DENGAN DAN TANPA MAPPER
 * ═══════════════════════════════════════════════════════════════════════════
 */

// ❌ SKENARIO 1: TANPA MAPPER (BAD PRACTICE)
// ═════════════════════════════════════════════════════════════════════════

/*

// Domain Model - KOTOR dengan Firebase
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(): FirebaseUser  // ❌ Domain tahu Firebase!
}

// UseCase - KOTOR dengan Firebase
class LoginUseCase(private val repo: AuthRepository) {
    suspend fun invoke(): FirebaseUser {  // ❌ Business logic tahu Firebase!
        val user = repo.login()
        
        // Business logic jadi ribet karena FirebaseUser punya struktur aneh
        if (user.email?.contains("@") == true) {  // ❌ Null safety ribet
            return user
        }
        throw Exception("Invalid")
    }
}

// ViewModel - KOTOR dengan Firebase
class LoginViewModel(private val useCase: LoginUseCase) {
    fun login() {
        viewModelScope.launch {
            val firebaseUser = useCase.invoke()  // ❌ ViewModel tahu Firebase!
            
            // UI logic harus handle FirebaseUser
            _userName.value = firebaseUser.displayName ?: "Unknown"
        }
    }
}

MASALAH:
1. ❌ Semua layer (Domain, UseCase, ViewModel) import Firebase
2. ❌ Kalau Firebase update struktur FirebaseUser → SEMUA RUSAK
3. ❌ Tidak bisa unit test tanpa Firebase SDK (lambat & ribet)
4. ❌ Migrasi ke backend lain = REWRITE SEMUA LAYER
5. ❌ Null safety ribet karena FirebaseUser.displayName bisa null

*/


// ✅ SKENARIO 2: DENGAN MAPPER (BEST PRACTICE)
// ═════════════════════════════════════════════════════════════════════════

/*

// Domain Model - BERSIH, tidak tahu Firebase
data class AuthUser(
    val uid: String,           // ✅ Plain Kotlin
    val email: String?,        // ✅ Kontrol sendiri nullability
    val name: String?          // ✅ Nama field sesuai bisnis kita
)

// Domain Repository Interface - BERSIH
interface AuthRepository {
    suspend fun login(): Result<AuthUser>  // ✅ Domain Model murni
}

// UseCase - BERSIH, hanya business logic
class LoginUseCase(private val repo: AuthRepository) {
    suspend fun invoke(): Result<AuthUser> {
        val result = repo.login()
        
        // Business logic jelas & simple
        return result.mapCatching { user ->
            if (user.email?.contains("@") == true) {
                user
            } else {
                throw Exception("Invalid email")
            }
        }
    }
}

// ViewModel - BERSIH, hanya UI logic
class LoginViewModel(private val useCase: LoginUseCase) {
    fun login() {
        viewModelScope.launch {
            val result = useCase.invoke()  // ✅ Dapat AuthUser
            
            result.onSuccess { authUser ->
                _userName.value = authUser.name ?: "Unknown"  // ✅ Simple!
            }
        }
    }
}

// Data Layer - SATU-SATUNYA yang tahu Firebase
class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(): Result<AuthUser> {
        return try {
            val firebaseUser = remoteDataSource.login()  // Firebase object
            val authUser = UserMapper.fromFirebaseUser(firebaseUser)  // ✅ MAPPING!
            Result.success(authUser)  // Domain Model
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Mapper - TRANSLATOR antara Firebase dan Domain
object UserMapper {
    fun fromFirebaseUser(fbUser: FirebaseUser): AuthUser {
        return AuthUser(
            uid = fbUser.uid,
            email = fbUser.email,
            name = fbUser.displayName ?: "Guest"  // ✅ Handle default di sini
        )
    }
}

KEUNTUNGAN:
1. ✅ Domain/UseCase/ViewModel TIDAK TAHU Firebase
2. ✅ Kalau Firebase update → cukup ubah Mapper & Data Layer
3. ✅ Unit test mudah - mock AuthUser tanpa Firebase
4. ✅ Migrasi backend = ubah Data Layer saja, sisanya AMAN
5. ✅ Null safety & default value dihandle di Mapper

*/


// ═══════════════════════════════════════════════════════════════════════════
// CONTOH TESTING: BEDANYA SANGAT JELAS!
// ═══════════════════════════════════════════════════════════════════════════

/*

// ❌ Test TANPA Mapper - RIBET!
@Test
fun testLoginUseCase() {
    // Harus mock Firebase!
    val mockFirebaseUser = mockk<FirebaseUser> {
        every { uid } returns "123"
        every { email } returns "test@test.com"
        every { displayName } returns "Test User"
        // ... masih banyak field Firebase yang harus di-mock
    }
    
    val mockRepo = mockk<AuthRepository> {
        coEvery { login() } returns mockFirebaseUser
    }
    
    // Test jadi lambat & ribet karena Firebase object besar
}


// ✅ Test DENGAN Mapper - MUDAH!
@Test
fun testLoginUseCase() {
    // Mock domain model - SIMPLE!
    val mockAuthUser = AuthUser(
        uid = "123",
        email = "test@test.com",
        name = "Test User"
    )
    
    val mockRepo = mockk<AuthRepository> {
        coEvery { login() } returns Result.success(mockAuthUser)
    }
    
    // Test cepat & simple!
}

*/


/**
 * ═══════════════════════════════════════════════════════════════════════════
 * KESIMPULAN SEDERHANA:
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Mapper = Tembok Pembatas antara "Dunia Luar" dan "Dunia Dalam" aplikasi
 *
 * DUNIA LUAR (Data Layer):
 * - Firebase, REST API, Room Database
 * - DTO, FirebaseUser, API Response
 * - Library eksternal
 *
 * MAPPER = TEMBOK PEMBATAS
 *
 * DUNIA DALAM (Domain & Presentation):
 * - Business Logic (UseCase)
 * - UI Logic (ViewModel)
 * - Domain Model yang BERSIH
 * - TIDAK TAHU tentang Firebase/API/Database
 *
 * Dengan Mapper, Anda bisa ganti "Dunia Luar" kapan saja tanpa merusak
 * "Dunia Dalam". Ini membuat aplikasi FLEXIBLE, TESTABLE, dan MAINTAINABLE.
 *
 */

