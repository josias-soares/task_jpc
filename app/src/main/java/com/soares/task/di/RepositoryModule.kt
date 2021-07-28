package com.soares.task.di

import com.soares.task.data.TaskRepositoryImpl
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.repositories.datasources.TaskCacheDataSource
import com.soares.task.domain.repositories.datasources.TaskNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTaskRepository(
        cacheDataSource: TaskCacheDataSource,
        networkDataSource: TaskNetworkDataSource
    ): TaskRepository {
        return TaskRepositoryImpl(cacheDataSource, networkDataSource)
    }
}