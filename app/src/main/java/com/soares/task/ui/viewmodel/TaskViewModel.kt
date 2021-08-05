package com.soares.task.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soares.task.domain.DataState
import com.soares.task.domain.models.Task
import com.soares.task.domain.usecases.DoSaveTask
import com.soares.task.domain.usecases.DoUpdateTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TaskViewModel
@Inject
constructor(
    private val doSaveTask: DoSaveTask,
    private val doUpdateTask: DoUpdateTask
) : ViewModel() {

    private val _task: MutableLiveData<Task> = MutableLiveData()
    val task: MutableLiveData<Task> get() = _task

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _createdTask: MutableLiveData<Boolean> = MutableLiveData()
    val createdTask: LiveData<Boolean> get() = _createdTask

    fun changeId(id: Long) = _id.postValue(id)
    private val _id: MutableLiveData<Long> = MutableLiveData()
    val id: LiveData<Long> get() = _id

    fun changeDescription(description: String) = _description.postValue(description)
    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: LiveData<String> get() = _description

    fun changePriorityId(priorityId: Int) = _priorityId.postValue(priorityId)
    private val _priorityId: MutableLiveData<Int> = MutableLiveData()
    val priorityId: LiveData<Int> get() = _priorityId

    fun changeDueDate(dueDate: String) = _dueDate.postValue(dueDate)
    private val _dueDate: MutableLiveData<String> = MutableLiveData()
    val dueDate: LiveData<String> get() = _dueDate

    fun changeComplete(complete: Boolean) = _complete.postValue(complete)
    private val _complete: MutableLiveData<Boolean> = MutableLiveData()
    val complete: LiveData<Boolean> get() = _complete

    fun setTask(t: Task) {
        _task.value = t
    }

    fun saveTask() {
        _task.postValue(
            Task(
                id.value ?: -1,
                description.value ?: "",
                priorityId.value ?: 0,
                dueDate.value ?: "",
                complete.value ?: false,
            )
        )

        viewModelScope.launch {
            task.value?.let {
                if (it.id > 0)
                    doUpdateTask(it)
                        .onEach { dataState ->
                            _loading.postValue(false)
                            when (dataState) {
                                is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                                is DataState.Loading -> _loading.postValue(true)
                                is DataState.Success -> _createdTask.postValue(true)
                            }
                        }
                        .launchIn(viewModelScope)
                else
                    doSaveTask(it)
                        .onEach { dataState ->
                            _loading.postValue(false)
                            when (dataState) {
                                is DataState.Error -> _errorMessage.postValue(dataState.exception.message)
                                is DataState.Loading -> _loading.postValue(true)
                                is DataState.Success -> _createdTask.postValue(true)
                            }
                        }
                        .launchIn(viewModelScope)
            }
        }
    }
}