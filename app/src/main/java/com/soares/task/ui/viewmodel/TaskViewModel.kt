package com.soares.task.ui.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.usecases.DoSaveTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class TaskViewModel
@ViewModelInject
constructor(
    private val doSaveTask: DoSaveTask,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _task: MutableLiveData<Task> = MutableLiveData()
    val task: LiveData<Task> get() = _task

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    fun addTask(task: Task) {
        viewModelScope.launch {
            doSaveTask(task)
                .onEach { dataState ->
                    _loading.postValue(false)
                    when (dataState) {
                        is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                        is DataState.Loading -> _loading.postValue(true)
                        is DataState.Success -> _task.postValue(dataState.data)
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}