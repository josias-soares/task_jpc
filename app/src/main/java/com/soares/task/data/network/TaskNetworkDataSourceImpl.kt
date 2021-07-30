package com.soares.task.data.network

import com.soares.task.data.network.mappers.TaskNetworkMapper
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.datasources.TaskNetworkDataSource
import com.soares.task.domain.services.TaskService
import com.soares.task.shared.Constants.ErrorMessage.ReturnsNull
import kotlinx.coroutines.delay

class TaskNetworkDataSourceImpl constructor(
    private val service: TaskService,
    private val taskNetworkMapper: TaskNetworkMapper
) : TaskNetworkDataSource {
    override suspend fun addTask(request: Task): Task? {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(800)

                Result.success(request).getOrNull()
                // TODO: 28/07/2021 fake

                //service.addTask(taskNetworkMapper.mapToEntity(request))
            }.onSuccess { result ->
                if (result == null) throw Exception(ReturnsNull)

                return taskNetworkMapper.mapFromEntity(taskNetworkMapper.mapToEntity(request))
            }.onFailure { error: Throwable ->
                throw error
            }
        } catch (e: Exception) {
            throw Exception(ReturnsNull)
        }

        return null
    }

    override suspend fun updateTask(request: Task): Task? {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(800)
                val result = TaskNetwork(
                    description = request.description,
                    priorityId = request.priorityId,
                    dueDate = request.dueDate,
                    complete = request.complete,
                )

                Result.success(result).getOrNull()
                // TODO: 28/07/2021 fake

                //service.updateTask(taskNetworkMapper.mapToEntity(request))
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

    override suspend fun removeTask(request: Task): Boolean {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(400)
                Result.success(true).getOrNull()
                // TODO: 28/07/2021 fake

                //service.removeTask(request.id)
            }.onSuccess { result ->
                if (result == null) throw Exception(ReturnsNull)

                return result
            }.onFailure { error: Throwable ->
                throw error
            }
        } catch (e: Exception) {
            throw Exception(ReturnsNull)
        }

        return true
    }

    override suspend fun consultAllTask(): List<Task>? {
        try {
            runCatching {
                // TODO: 28/07/2021 fake
                delay(800)

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