package com.soares.task.domain.datasources

import com.soares.task.domain.models.Task

interface TaskCacheDataSource {
    suspend fun insert(task: Task): Long
    suspend fun get(id: Long): Task?
    suspend fun delete(id: Long)
    suspend fun deleteAll()
}