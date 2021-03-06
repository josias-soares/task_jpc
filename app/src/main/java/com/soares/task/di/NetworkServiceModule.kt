package com.soares.task.di

import com.soares.task.data.network.services.TaskApi
import com.soares.task.data.network.services.TaskServiceImpl
import com.soares.task.domain.services.TaskService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Singleton
    @Provides
    fun provideCardService(
        api: TaskApi
    ): TaskService {
        return TaskServiceImpl(api)
    }
}




















