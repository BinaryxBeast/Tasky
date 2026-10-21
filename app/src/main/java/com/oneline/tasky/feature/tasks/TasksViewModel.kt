package com.oneline.tasky.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneline.tasky.core.data.TaskRepository
import com.oneline.tasky.core.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksViewModel (
    private val repository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TasksUiState())

    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.tasks.collect {
                tasks ->
                _uiState.update {
                    it.copy(
                        tasks = tasks,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.addTask(title.trim())
        }
    }

    fun toggleTask(task: Task){
        viewModelScope.launch {
            repository.updateTask(
                task.copy(isCompleted = !task.isCompleted)
            )
        }
    }

    fun deleteTask(taskId: Int){
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }
}