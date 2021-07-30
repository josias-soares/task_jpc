package com.soares.task.domain.usecases

import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTasks
@Inject
constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(): Flow<DataState<List<Task>>> = flow {
        emit(DataState.Loading)
        try {
            val tasks = taskRepository.consultAllTask()

            emit(DataState.Success(tasks))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

}