package com.samridhi.vitalstrackerapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals_data")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val heartRate: String,
    val bloodPressure: String,
    val babyKicks: String,
    val weight: String,
    val timeStamp: String
)