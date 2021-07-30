package com.soares.task.viewmodels

import com.nhaarman.mockitokotlin2.any
import com.soares.task.BaseTest
import com.soares.task.domain.DataState
import com.soares.task.domain.usecases.DoSaveTask
import com.soares.task.domain.usecases.DoUpdateTask
import com.soares.task.newTaskMock
import com.soares.task.taskMock
import com.soares.task.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`


@ExperimentalCoroutinesApi
class TaskViewModelTest : BaseTest() {
    lateinit var viewModel: TaskViewModel

    @Mock
    lateinit var doSaveTask: DoSaveTask

    @Mock
    lateinit var doUpdateTask: DoUpdateTask

    @Before
    override fun setup() {
        super.setup()

        viewModel = TaskViewModel(
            doSaveTask,
            doUpdateTask
        )
    }

    //region "DoSaveTask"
    @Test
    fun `Saving new task, then assert that loading is true when DataState_Loading is emitted`() {
        // Arrange
        runBlocking {
            `when`(doSaveTask(newTaskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Loading)
                    }
                )
        }

        // Act
        viewModel.setTask(newTaskMock)
        viewModel.saveTask()

        // Assert
        assertEquals(viewModel.loading.value, true)
    }

    @Test
    fun `Saving new task, then assert that the errorMessage is not empty when an exception is returned`() {
        // Arrange
        runBlocking {
            `when`(doSaveTask(any()))
                .thenReturn(
                    flow {
                        emit(DataState.Error(Exception("ERROR")))
                    }
                )
        }

        // Act
        viewModel.setTask(newTaskMock)
        viewModel.saveTask()

        // Assert
        assert(viewModel.errorMessage.value != null)
    }

    @Test
    fun `Saving new task, then assert that createdTask is true when then success`() {
        // Arrange
        runBlocking {
            `when`(doSaveTask(newTaskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Success(newTaskMock))
                    }
                )
        }

        // Act
        viewModel.setTask(newTaskMock)
        viewModel.saveTask()

        // Assert
        assertEquals(viewModel.createdTask.value, true)
    }
    //endregion

    //region "DoUpdateTask"
    @Test
    fun `Editing task, then assert that loading is true when DataState_Loading is emitted`() {
        // Arrange
        runBlocking {
            `when`(doUpdateTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Loading)
                    }
                )
        }

        // Act
        viewModel.setTask(taskMock)
        viewModel.saveTask()

        // Assert
        assertEquals(viewModel.loading.value, true)
    }

    @Test
    fun `Editing task, then assert that the errorMessage is not empty when an exception is returned`() {
        // Arrange
        runBlocking {
            `when`(doUpdateTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Error(Exception("ERROR")))
                    }
                )
        }

        // Act
        viewModel.setTask(taskMock)
        viewModel.saveTask()

        // Assert
        assert(viewModel.errorMessage.value != null)
    }

    @Test
    fun `Editing task, then assert that createdTask is true when then success`() {
        // Arrange
        runBlocking {
            `when`(doUpdateTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Success(taskMock))
                    }
                )
        }

        // Act
        viewModel.setTask(taskMock)
        viewModel.saveTask()

        // Assert
        assertEquals(viewModel.createdTask.value, true)
    }
    //endregion
}