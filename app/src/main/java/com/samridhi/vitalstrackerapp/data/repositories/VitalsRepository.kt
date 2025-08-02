package com.samridhi.vitalstrackerapp.data.repositories

import com.samridhi.vitalstrackerapp.data.local.dao.VitalsDao
import com.samridhi.vitalstrackerapp.data.local.entity.VitalsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VitalsRepository @Inject constructor(
    private val vitalsDao: VitalsDao,
) {

    suspend fun getAllVitals() = vitalsDao.getAllVitals()

    suspend fun insertVitals(
        heartRate: String,
        bloodPressure: String,
        babyKicks: String,
        weight: String,
    ) {
        val newVitals = VitalsEntity(
            heartRate = heartRate,
            bloodPressure = bloodPressure,
            babyKicks = babyKicks,
            weight = weight,
            timeStamp = System.currentTimeMillis().toString(),
        )
        vitalsDao.insertVitals(newVitals)
    }
}
