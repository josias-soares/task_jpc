package com.soares.task.di

import com.soares.task.data.cache.TaskDatabase
import com.soares.task.data.cache.daos.TaskDao
import com.soares.task.data.cache.services.TaskDaoServiceImpl
import com.soares.task.domain.services.TaskDaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Singleton
    @Provides
    fun provideTaskDaoService(
        dao: TaskDao
    ): TaskDaoService {
        return TaskDaoServiceImpl(dao)
    }
}

























