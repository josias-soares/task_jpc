package com.soares.task.domain.usecases

import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveTask
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(task: Task): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val tasks = taskRepository.removeTask(task)

            emit(DataState.Success(tasks))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}