package com.soares.task.di

import com.soares.task.data.network.services.TaskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkApiModule {
    @Singleton
    @Provides
    fun provideTaskApi(retrofit: Retrofit): TaskApi {
        return retrofit
            .create(TaskApi::class.java)
    }
}




















