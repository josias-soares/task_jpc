package com.soares.task.di

import android.content.Context
import com.soares.task.data.cache.*
import com.soares.task.data.cache.mappers.TaskCacheMapper
import com.soares.task.domain.data.TaskCacheDataSource
import com.soares.task.domain.services.TaskDaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPrefs {
        return SharedPrefs(context)
    }

    @Singleton
    @Provides
    fun provideTaskCacheDataSource(
        taskDaoService: TaskDaoService,
        taskCacheMapper: TaskCacheMapper
    ): TaskCacheDataSource {
        return TaskCacheDataSourceImpl(taskDaoService, taskCacheMapper)
    }
}

























