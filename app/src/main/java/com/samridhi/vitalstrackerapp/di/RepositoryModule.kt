package com.samridhi.vitalstrackerapp.di

import com.samridhi.vitalstrackerapp.data.local.dao.VitalsDao
import com.samridhi.vitalstrackerapp.data.repositories.VitalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideVitalsRepository(vitalsDao: VitalsDao) = VitalsRepository(vitalsDao)
}