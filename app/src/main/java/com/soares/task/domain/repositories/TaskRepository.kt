package com.soares.task.domain.repositories

import com.soares.task.domain.models.Task

interface TaskRepository {
    suspend fun addTask(task: Task): Task?
    suspend fun updateTask(task: Task): Task?
    suspend fun removeTask(task: Task): Boolean
    suspend fun consultAllTask(): List<Task>
    suspend fun consultTask(id: Long): Task?
}