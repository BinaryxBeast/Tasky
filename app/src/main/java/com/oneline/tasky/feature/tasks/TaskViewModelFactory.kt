package com.oneline.tasky.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oneline.tasky.core.data.TaskRepository

class TaskViewModelFactory(
    private val repository: TaskRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)){
            return TasksViewModel(repository) as T
        }
        throw IllegalArgumentException(
            "Unknown ViewModel: ${modelClass.name}"
        )
    }
}