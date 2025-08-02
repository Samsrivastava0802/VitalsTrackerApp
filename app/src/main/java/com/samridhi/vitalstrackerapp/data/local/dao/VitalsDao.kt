package com.samridhi.vitalstrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.samridhi.vitalstrackerapp.data.local.entity.VitalsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Query("SELECT * FROM vitals_data ORDER BY timeStamp DESC")
    suspend fun getAllVitals(): List<VitalsEntity>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: VitalsEntity)

}