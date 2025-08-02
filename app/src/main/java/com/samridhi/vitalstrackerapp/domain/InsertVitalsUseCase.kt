package com.samridhi.vitalstrackerapp.domain

import com.samridhi.vitalstrackerapp.data.repositories.VitalsRepository
import javax.inject.Inject

class InsertVitalsUseCase @Inject constructor(
    private val vitalsRepository: VitalsRepository,
) {
    suspend operator fun invoke(
        heartRate: String,
        bloodPressure : String,
        babyKicks : String,
        weight : String
    ) {
        vitalsRepository.insertVitals(
            heartRate = heartRate,
            bloodPressure = bloodPressure,
            babyKicks = babyKicks,
            weight = weight
        )
    }
}