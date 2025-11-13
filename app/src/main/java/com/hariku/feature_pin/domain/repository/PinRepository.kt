package com.hariku.feature_pin.domain.repository

import com.hariku.feature_pin.data.local.dao.PinDao
import com.hariku.feature_pin.data.local.entity.Pin
import kotlinx.coroutines.flow.Flow

class PinRepository(private val pinDao: PinDao) {
    fun getPin(): Flow<Pin?> {
        return pinDao.getPin()
    }

    suspend fun insertPin(pin: Pin) {
        pinDao.insertPin(pin)
    }

    suspend fun updatePin(pin: Pin) {
        pinDao.updatePin(pin)
    }

    suspend fun deletePin(pin: Pin) {
        pinDao.deletePin(pin)
    }
}