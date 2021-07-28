package com.soares.task.data.cache

import com.soares.task.data.cache.mappers.TaskCacheMapper
import com.soares.task.domain.datasources.TaskCacheDataSource
import com.soares.task.domain.services.TaskDaoService
import com.soares.task.domain.models.Task


class TaskCacheDataSourceImpl
constructor(
    private val daoService: TaskDaoService,
    private val cacheMapper: TaskCacheMapper
) : TaskCacheDataSource {

    override suspend fun insert(task: Task): Long {
        return daoService.insert(cacheMapper.mapToEntity(task))
    }

    override suspend fun get(id: Long): Task? {
        val task = daoService.get(id)

        return task?.let { cacheMapper.mapFromEntity(it) }
    }

    override suspend fun delete(id: Long) {
        daoService.delete(id)
    }

    override suspend fun deleteAll() {
        daoService.deleteAll()
    }
}