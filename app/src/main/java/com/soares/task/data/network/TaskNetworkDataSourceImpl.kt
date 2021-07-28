package com.soares.task.data.network

import com.soares.task.data.network.mappers.TaskNetworkMapper
import com.soares.task.domain.models.Task
import com.soares.task.domain.services.TaskService
import com.soares.task.domain.datasources.TaskNetworkDataSource
import com.soares.task.shared.Constants.ErrorMessage.ReturnsNull
import kotlinx.coroutines.delay

class TaskNetworkDataSourceImpl constructor(
    private val service: TaskService,
    private val taskNetworkMapper: TaskNetworkMapper
) : TaskNetworkDataSource {
    override suspend fun addTask(request: AddTaskRequest): Task? {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(1500)
                val result = TaskNetwork(
                    description = request.description,
                    priorityId = request.priorityId,
                    dueDate = request.dueDate,
                    complete = request.complete,
                )

                Result.success(result).getOrNull()
                // TODO: 28/07/2021 fake


                //service.addTask(request)
            }.onSuccess { result ->
                if (result == null) throw Exception(ReturnsNull)

                return taskNetworkMapper.mapFromEntity(result)
            }.onFailure { error: Throwable ->
                throw error
            }
        } catch (e: Exception) {
            throw Exception(ReturnsNull)
        }

        return null
    }

    override suspend fun consultAllTask(): List<Task>? {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(1500)

                Result.success(arrayListOf<TaskNetwork>()).getOrNull()
                // TODO: 28/07/2021 fake


                //service.consultAllTask()
            }.onSuccess { result ->
                return taskNetworkMapper.mapFromEntityList(result)
            }.onFailure { error: Throwable ->
                throw error
            }
        } catch (e: Exception) {
            throw throw Exception(ReturnsNull)
        }

        return null
    }

    override suspend fun consultTask(request: Long): Task? {
        try {
            runCatching {
                service.consultTask(request)
            }.onSuccess { result ->
                if (result == null) throw Exception(ReturnsNull)

                return taskNetworkMapper.mapFromEntity(result)
            }.onFailure { error: Throwable ->
                throw error
            }
        } catch (e: Exception) {
            throw throw Exception(ReturnsNull)
        }

        return null
    }
}