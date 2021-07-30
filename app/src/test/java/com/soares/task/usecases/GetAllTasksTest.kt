package com.soares.task.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.soares.task.BaseTest
import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.usecases.GetAllTasks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


@ExperimentalCoroutinesApi
class GetAllTasksTest : BaseTest() {
    @Mock
    private lateinit var repository: TaskRepository

    private lateinit var getAllTasks: GetAllTasks

    @Before
    override fun setup() {
        super.setup()
        getAllTasks = GetAllTasks(repository)
    }

    @Test
    fun `Getting tasks, then, assert that DataState_Loading is called one time`() = runBlocking {
        // Arrange
        whenever(repository.consultAllTask())
            .thenReturn(ArrayList())

        // Act
        val stateResults = getAllTasks().toList()

        // Assert
        assertEquals(stateResults.filter { it == DataState.Loading }.size, 1)
    }

    @Test
    fun `Getting task, then, assert that DataState_Success is called when the result is successful`() =
        runBlocking {
            // Arrange
            val task = ArrayList<Task>()
            whenever(repository.consultAllTask())
                .thenReturn(task)

            // Act
            val stateResults = getAllTasks().toList()

            // Assert
            assertEquals(
                stateResults.first { it == DataState.Success(task) },
                DataState.Success(task)
            )
        }

    @Test
    fun `Getting task, then, assert that DataState_Error is called when return error from repository`() =
        runBlocking {
            // Arrange
            val dataStateError = DataState.Error(NullPointerException())
            whenever(repository.consultAllTask())
                .thenThrow(dataStateError.exception)

            // Act
            val stateResults = getAllTasks().toList()

            // Assert
            assertEquals(stateResults.toList(), mutableListOf(DataState.Loading, dataStateError))
        }
}