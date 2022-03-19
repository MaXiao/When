package com.xiaoism.time.di

import android.content.Context
import androidx.room.Room
import com.xiaoism.time.repository.TimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TimeDatabase {
        return Room.databaseBuilder(
            context,
            TimeDatabase::class.java,
            "when.db"
        ).createFromAsset("database/times.db")
            .build()
    }

    @Singleton
    @Provides
    fun providePersonDao(db: TimeDatabase) = db.personDao

    @Singleton
    @Provides
    fun provideCityDao(db: TimeDatabase) = db.cityDao

    @Singleton
    @Provides
    fun provideGroupDao(db: TimeDatabase) = db.groupDao
}