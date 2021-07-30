package com.soares.task.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.soares.task.BaseTest
import com.soares.task.domain.DataState
import com.soares.task.domain.repositories.TaskRepository
import com.soares.task.domain.usecases.DoSaveTask
import com.soares.task.taskMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.lenient


@ExperimentalCoroutinesApi
class DoSaveTaskTest : BaseTest() {
    @Mock
    private lateinit var repository: TaskRepository

    private lateinit var doSaveTask: DoSaveTask

    @Before
    override fun setup() {
        super.setup()
        doSaveTask = DoSaveTask(repository)
    }

    @Test
    fun `Saving task, then, assert that DataState_Loading is called one time`() = runBlocking {
        // Arrange
        lenient().`when`(repository.addTask(any()))
            .thenReturn(any())

        // Act
        val stateResults = doSaveTask(any()).toList()

        // Assert
        assertEquals(stateResults.filter { it == DataState.Loading }.size, 1)
    }

    @Test
    fun `Saving task, then assert that DataState_Success is called and return is same that was send in request when the result is successful`() =
        runBlocking {
            // Arrange
            val task = taskMock
            whenever(repository.addTask(task))
                .thenReturn(task)

            // Act
            val stateResults = doSaveTask(task).toList()

            // Assert
            assertEquals(
                stateResults.first { it == DataState.Success(task) },
                DataState.Success(task)
            )
        }

    @Test
    fun `Saving task, then, assert that DataState_Error is called when return error from repository`() =
        runBlocking {
            // Arrange
            val dataStateError = DataState.Error(NullPointerException())
            whenever(repository.addTask(taskMock))
                .thenThrow(dataStateError.exception)

            // Act
            val stateResults = doSaveTask(taskMock).toList()

            // Assert
            assertEquals(stateResults.toList(), mutableListOf(DataState.Loading, dataStateError))
        }
}