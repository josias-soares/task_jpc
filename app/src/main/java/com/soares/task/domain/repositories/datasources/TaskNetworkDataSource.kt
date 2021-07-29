package com.soares.task.domain.repositories.datasources

import com.soares.task.domain.models.Task

interface TaskNetworkDataSource {
    suspend fun addTask(request: Task): Task?
    suspend fun updateTask(request: Task): Task?
    suspend fun consultAllTask(): List<Task>?
    suspend fun consultTask(request: Long): Task?
}