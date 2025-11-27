package com.hariku.feature_pin.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hariku.feature_pin.data.local.entity.Pin
import kotlinx.coroutines.flow.Flow

@Dao
interface PinDao{
    // CREATE: Menyimpan data baru
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPin(pin: Pin)

    // READ: Mengambil Pin
    @Query("SELECT * FROM pin_table WHERE id = 0")
    fun getPin(): Flow<Pin?>

    // UPDATE: Memperbarui pin
    @Update
    suspend fun updatePin(pin: Pin)

    // DELETE: Menghapus Pin
    @Delete
    suspend fun deletePin(pin: Pin)
}