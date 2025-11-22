package com.hariku.feature_pin.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pin_table")
data class Pin(
    @PrimaryKey
    val id: Int = 0,

    //Isi Pin
    val pin: String
)