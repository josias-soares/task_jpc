package com.soares.task.viewmodels

import com.nhaarman.mockitokotlin2.verify
import com.soares.task.BaseTest
import com.soares.task.domain.DataState
import com.soares.task.domain.usecases.DoUpdateTask
import com.soares.task.domain.usecases.GetAllTasks
import com.soares.task.domain.usecases.RemoveTask
import com.soares.task.listTaskMock
import com.soares.task.taskMock
import com.soares.task.ui.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times


@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTest() {
    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var getAllTasks: GetAllTasks

    @Mock
    lateinit var removeTask: RemoveTask

    @Mock
    lateinit var doUpdateTask: DoUpdateTask

    @Before
    override fun setup() {
        super.setup()

        viewModel = MainViewModel(
            getAllTasks,
            removeTask,
            doUpdateTask
        )
    }

    //region "GetAllTasks"
    @Test
    fun `Get All Tasks, then assert that loading is true when DataState_Loading is emitted`() {
        // Arrange
        runBlocking {
            `when`(getAllTasks())
                .thenReturn(
                    flow {
                        emit(DataState.Loading)
                    }
                )
        }

        // Act
        viewModel.getAll()

        // Assert
        assertEquals(viewModel.loading.value, true)
    }

    @Test
    fun `Get All Tasks, then assert that the errorMessage is not empty when an exception is returned`() {
        // Arrange
        runBlocking {
            `when`(getAllTasks())
                .thenReturn(
                    flow {
                        emit(DataState.Error(Exception("ERROR")))
                    }
                )
        }

        // Act
        viewModel.getAll()

        // Assert
        assert(viewModel.errorMessage.value != null)
    }

    @Test
    fun `Get All Tasks, then assert that tasks is empty when then success`() {
        // Arrange
        runBlocking {
            `when`(getAllTasks())
                .thenReturn(
                    flow {
                        emit(DataState.Success(listTaskMock))
                    }
                )
        }

        // Act,
        viewModel.getAll()

        // Assert
        assert(viewModel.tasks.value!!.isNotEmpty())
        assert(listTaskMock.size == viewModel.tasks.value?.size)
    }
    //endregion

    //region "RemoveTask"
    @Test
    fun `Remove Task, then assert that loading is true when DataState_Loading is emitted`() {
        // Arrange
        runBlocking {
            `when`(removeTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Loading)
                    }
                )
        }

        // Act
        viewModel.deleteTask(taskMock)

        // Assert
        assertEquals(viewModel.loading.value, true)
    }

    @Test
    fun `Remove Task, then assert that the errorMessage is not empty when an exception is returned`() {
        // Arrange
        runBlocking {
            `when`(removeTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Error(Exception("ERROR")))
                    }
                )
        }

        // Act
        viewModel.deleteTask(taskMock)

        // Assert
        assert(viewModel.errorMessage.value != null)
    }

    @Test
    fun `Remove Task, then assert that GetAll method is called when then success`() {
        // Arrange
        runBlocking {
            `when`(removeTask(taskMock))
                .thenReturn(
                    flow {
                        emit(DataState.Success(true))
                    }
                )
        }

        // Act,
        val vm = Mockito.spy(viewModel)
        vm.deleteTask(taskMock)

        // Assert
        verify(vm, times(1)).getAll()
    }
    //endregion

    //region "DoUpdateTask"
    @Test
    fun `Complete task, then assert that loading is true when DataState_Loading is emitted`() {
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
        viewModel.completeTask(taskMock)

        // Assert
        assertEquals(true, viewModel.loading.value)
    }

    @Test
    fun `Complete task, then assert that the errorMessage is not empty when an exception is returned`() {
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
        viewModel.completeTask(taskMock)

        // Assert
        assert(viewModel.errorMessage.value != null)
    }

    @Test
    fun `Complete task, then assert that changedTask is true when then success`() {
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
        viewModel.completeTask(taskMock)

        // Assert
        assertEquals(viewModel.changedTask.value, true)
    }
    //endregion
}