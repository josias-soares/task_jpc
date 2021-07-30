package com.soares.task.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.usecases.DoUpdateTask
import com.soares.task.domain.usecases.GetAllTasks
import com.soares.task.domain.usecases.RemoveTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getAllTasks: GetAllTasks,
    private val removeTask: RemoveTask,
    private val doUpdateTask: DoUpdateTask,
) : ViewModel() {

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: MutableLiveData<List<Task>> get() = _tasks

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _changedTask: MutableLiveData<Boolean> = MutableLiveData()
    val changedTask: LiveData<Boolean> get() = _changedTask

    fun getAll() {
        viewModelScope.launch {
            getAllTasks()
                .onEach { dataState ->
                    _loading.postValue(false)
                    when (dataState) {
                        is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                        is DataState.Loading -> _loading.postValue(true)
                        is DataState.Success -> _tasks.postValue(dataState.data)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            removeTask(task)
                .onEach { dataState ->
                    _loading.postValue(false)
                    when (dataState) {
                        is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                        is DataState.Loading -> _loading.postValue(true)
                        is DataState.Success -> {
                            getAll()
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun completeTask(task: Task) {
        viewModelScope.launch {
            doUpdateTask(task)
                .onEach { dataState ->
                    _loading.postValue(false)
                    when (dataState) {
                        is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                        is DataState.Loading -> _loading.postValue(true)
                        is DataState.Success -> _changedTask.postValue(true)
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}