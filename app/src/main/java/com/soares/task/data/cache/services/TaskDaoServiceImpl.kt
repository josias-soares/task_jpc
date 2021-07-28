package com.soares.task.data.cache.services

import com.soares.task.data.cache.tables.TaskCache
import com.soares.task.data.cache.daos.TaskDao
import com.soares.task.domain.services.TaskDaoService

class TaskDaoServiceImpl
constructor(
    private val dao: TaskDao
) : TaskDaoService {

    override suspend fun insert(taskCache: TaskCache): Long {
        return dao.insert(taskCache)
    }

    override suspend fun get(id: Long): TaskCache? {
        return dao.getById(id)
    }

    override suspend fun delete(id: Long) {
        return dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

}