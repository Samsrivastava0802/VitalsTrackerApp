package com.samridhi.vitalstrackerapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samridhi.vitalstrackerapp.data.local.dao.VitalsDao
import com.samridhi.vitalstrackerapp.data.local.entity.VitalsEntity


@Database(entities = [VitalsEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
}