package com.soares.task.data.network.services

import com.soares.task.data.network.AddTaskRequest
import com.soares.task.data.network.TaskNetwork
import com.soares.task.domain.services.TaskService

class TaskServiceImpl
constructor(
    private val serviceApi: TaskApi
) : TaskService {

    override suspend fun addTask(request: AddTaskRequest): TaskNetwork? {
        return serviceApi.addTask(request)
    }

    override suspend fun consultAllTask(): List<TaskNetwork>? {
        return serviceApi.consultAllTask()
    }

    override suspend fun consultTask(id: Long): TaskNetwork? {
        return serviceApi.consultTask(id)
    }
}