package com.soares.task.data

import android.content.Context
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.repositories.datasources.TaskCacheDataSource
import com.soares.task.domain.repositories.datasources.TaskNetworkDataSource
import com.soares.task.shared.ConnectionHelper
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl
@Inject
constructor(
    private val cacheDataSource: TaskCacheDataSource,
    private val networkDataSource: TaskNetworkDataSource,
    private val context: Context
) : TaskRepository {
    override suspend fun addTask(task: Task): Task? {
        if (!ConnectionHelper.isConnectionAvailable(context)) {
            throw  WithoutInternetException()
        }

        val result = networkDataSource.addTask(task)

        result?.let {
            // TODO: 28/07/2021 Fake - simulating the return of the server's "id"
            result.id = Calendar.getInstance().timeInMillis


            cacheDataSource.insert(result)
        }

        return result
    }

    override suspend fun updateTask(task: Task): Task? {
        if (!ConnectionHelper.isConnectionAvailable(context)) {
            throw  WithoutInternetException()
        }

        networkDataSource.updateTask(task)

        return cacheDataSource.get(task.id)
    }

    override suspend fun consultAllTask(): List<Task> {
        if (ConnectionHelper.isConnectionAvailable(context)) {
            val result = networkDataSource.consultAllTask()

            result?.forEach { task ->
                cacheDataSource.insert(task)
            }
        }

        return cacheDataSource.getAll()
    }

    override suspend fun consultTask(id: Long): Task? {
        if (ConnectionHelper.isConnectionAvailable(context)) {
            val task = networkDataSource.consultTask(id)

            task?.let {
                cacheDataSource.insert(it)
            }
        }

        return cacheDataSource.get(id)
    }

}