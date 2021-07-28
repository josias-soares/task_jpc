package com.soares.task.domain.services

import com.soares.task.data.cache.tables.TaskCache

interface TaskDaoService {
    suspend fun insert(taskCache: TaskCache): Long
    suspend fun get(id: Long): TaskCache?
    suspend fun delete(id: Long)
    suspend fun deleteAll()
}
