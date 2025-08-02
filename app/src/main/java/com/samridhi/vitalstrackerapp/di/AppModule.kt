package com.samridhi.vitalstrackerapp.di

import android.content.Context
import androidx.room.Room
import com.samridhi.vitalstrackerapp.data.local.dao.VitalsDao
import com.samridhi.vitalstrackerapp.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    fun provideVitalsDao(database: AppDatabase): VitalsDao = database.vitalsDao()
}