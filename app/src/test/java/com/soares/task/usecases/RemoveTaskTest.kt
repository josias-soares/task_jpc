package com.soares.task.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.soares.task.BaseTest
import com.soares.task.domain.DataState
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.usecases.RemoveTask
import com.soares.task.taskMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.lenient


@ExperimentalCoroutinesApi
class RemoveTaskTest : BaseTest() {
    @Mock
    private lateinit var repository: TaskRepository

    private lateinit var removeTask: RemoveTask

    @Before
    override fun setup() {
        super.setup()
        removeTask = RemoveTask(repository)
    }

    @Test
    fun `Saving task, then, assert that DataState_Loading is called one time`() = runBlocking {
        // Arrange
        lenient().`when`(repository.removeTask(any()))
            .thenReturn(any())

        // Act
        val stateResults = removeTask(any()).toList()

        // Assert
        assertEquals(stateResults.filter { it == DataState.Loading }.size, 1)
    }

    @Test
    fun `Saving task, then assert that DataState_Success is called and return is same that was send in request when the result is successful`() =
        runBlocking {
            // Arrange
            val task = taskMock
            val result = true
            whenever(repository.removeTask(task))
                .thenReturn(result)

            // Act
            val stateResults = removeTask(task).toList()

            // Assert
            assertEquals(
                stateResults.first { it == DataState.Success(result) },
                DataState.Success(result)
            )
        }

    @Test
    fun `Saving task, then, assert that DataState_Error is called when return error from repository`() =
        runBlocking {
            // Arrange
            val dataStateError = DataState.Error(NullPointerException())
            whenever(repository.removeTask(any()))
                .thenThrow(dataStateError.exception)

            // Act
            val stateResults = removeTask(taskMock).toList()

            // Assert
            assertEquals(stateResults.toList(), mutableListOf(DataState.Loading, dataStateError))
        }
}