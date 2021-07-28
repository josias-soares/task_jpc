package com.soares.task.data

import com.soares.task.data.network.AddTaskRequest
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.repositories.datasources.TaskCacheDataSource
import com.soares.task.domain.repositories.datasources.TaskNetworkDataSource
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl
@Inject
constructor(
    private val cacheDataSource: TaskCacheDataSource,
    private val networkDataSource: TaskNetworkDataSource
) : TaskRepository {
    override suspend fun addTask(task: Task): Task? {
        val request = AddTaskRequest(
            description = task.description,
            priorityId = task.priorityId,
            dueDate = task.dueDate,
            complete = task.complete,
        )

        val result = networkDataSource.addTask(request)

        result?.let {
            // TODO: 28/07/2021 Fake - simulating the return of the server's "id"
            result.id = Calendar.getInstance().timeInMillis


            cacheDataSource.insert(result)
        }

        return result
    }

    override suspend fun consultAllTask(): List<Task> {
        val result = networkDataSource.consultAllTask()

        result?.forEach { task ->
            cacheDataSource.insert(task)
        }

        return cacheDataSource.getAll()
    }

    override suspend fun consultTask(id: Long): Task? {
        val task = networkDataSource.consultTask(id)

        task?.let {
            cacheDataSource.insert(it)
        }

        return cacheDataSource.get(id)
    }

}