package com.soares.task.domain.services

import com.soares.task.data.network.AddTaskRequest
import com.soares.task.data.network.TaskNetwork

interface TaskService {
    suspend fun addTask(request: AddTaskRequest): TaskNetwork?
    suspend fun consultAllTask(): List<TaskNetwork>?
    suspend fun consultTask(id: Long): TaskNetwork?
}