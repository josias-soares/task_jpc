package com.soares.task.di

import com.soares.task.data.network.TaskNetworkDataSourceImpl
import com.soares.task.data.network.mappers.TaskNetworkMapper
import com.soares.task.domain.data.TaskNetworkDataSource
import com.soares.task.domain.services.TaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkDataSourceModule {
    @Singleton
    @Provides
    fun provideTaskNetworkDataSource(
        service: TaskService,
        taskNetworkMapper: TaskNetworkMapper
    ): TaskNetworkDataSource {
        return TaskNetworkDataSourceImpl(
            service,
            taskNetworkMapper
        )
    }
}




















