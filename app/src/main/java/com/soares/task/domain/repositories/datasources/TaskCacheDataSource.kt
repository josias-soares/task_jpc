package com.soares.task.domain.repositories.datasources

import com.soares.task.domain.models.Task

interface TaskCacheDataSource {
    suspend fun insert(task: Task): Long
    suspend fun get(id: Long): Task?
    suspend fun delete(id: Long)
    suspend fun getAll(): List<Task>
    suspend fun deleteAll()
}