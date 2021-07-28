package com.soares.task.di

import android.content.Context
import androidx.room.Room
import com.soares.task.data.cache.TaskDatabase
import com.soares.task.data.cache.TaskDatabase.Companion.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): TaskDatabase {
        return Room
            .databaseBuilder(
                context,
                TaskDatabase::class.java,
                TaskDatabase.DATABASE_NAME
            )
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
}