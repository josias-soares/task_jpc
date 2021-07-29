package com.soares.task.domain.usecases

import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoUpdateTask
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(obj: Task): Flow<DataState<Task?>> = flow {
        emit(DataState.Loading)
        try {
            val task = taskRepository.updateTask(obj)

            emit(DataState.Success(task))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}