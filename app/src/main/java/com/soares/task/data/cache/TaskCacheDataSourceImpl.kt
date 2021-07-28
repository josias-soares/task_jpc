package com.soares.task.data.cache

import com.soares.task.data.cache.mappers.TaskCacheMapper
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.datasources.TaskCacheDataSource
import com.soares.task.domain.services.TaskDaoService


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
        return daoService.delete(id)
    }

    override suspend fun getAll(): List<Task> {
        val tasks = daoService.getAll()
        return tasks.let { cacheMapper.mapFromEntityList(it) }
    }

    override suspend fun deleteAll() {
        daoService.deleteAll()
    }
}