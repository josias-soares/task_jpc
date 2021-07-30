package com.soares.task.domain.services

import com.soares.task.data.network.TaskNetwork

interface TaskService {
    suspend fun addTask(request: TaskNetwork): TaskNetwork?
    suspend fun updateTask(request: TaskNetwork): TaskNetwork?
    suspend fun removeTask(id: Long): Any?
    suspend fun consultAllTask(): List<TaskNetwork>?
    suspend fun consultTask(id: Long): TaskNetwork?
}