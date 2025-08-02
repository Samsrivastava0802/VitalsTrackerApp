package com.samridhi.vitalstrackerapp.domain

import com.samridhi.vitalstrackerapp.data.repositories.VitalsRepository
import javax.inject.Inject

class GetAllVitalsUseCase @Inject constructor(
    private val vitalsRepository: VitalsRepository
) {
    suspend operator fun invoke() = vitalsRepository.getAllVitals()
}