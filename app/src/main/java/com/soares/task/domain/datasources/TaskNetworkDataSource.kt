package com.soares.task.domain.datasources

import com.soares.task.data.network.AddTaskRequest
import com.soares.task.domain.models.Task

interface TaskNetworkDataSource {
    suspend fun addTask(request: AddTaskRequest): Task?
    suspend fun consultAllTask(): List<Task>?
    suspend fun consultTask(request: Long): Task?
}