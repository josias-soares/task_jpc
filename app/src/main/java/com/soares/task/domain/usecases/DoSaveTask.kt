package com.soares.task.domain.usecases

import com.soares.task.domain.datasources.TaskCacheDataSource
import com.soares.task.data.network.AddTaskRequest
import com.soares.task.domain.DataState
import com.soares.task.domain.datasources.TaskNetworkDataSource
import com.soares.task.domain.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class DoSaveTask
@Inject
constructor(
    private val cacheDataSource: TaskCacheDataSource,
    private val networkDataSource: TaskNetworkDataSource
) {
    suspend operator fun invoke(obj: Task): Flow<DataState<Task?>> = flow {
        emit(DataState.Loading)
        try {

            val request = AddTaskRequest(
                description = obj.description,
                priorityId = obj.priorityId,
                dueDate = obj.dueDate,
                complete = obj.complete,
            )

            val task = networkDataSource.addTask(request)

            task?.let {
                // TODO: 28/07/2021 Fake - simulating the return of the server's "id"
                task.id = Calendar.getInstance().timeInMillis


                cacheDataSource.insert(task)
            }

            emit(DataState.Success(task))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}