package com.oneline.tasky.feature.tasks

import com.oneline.tasky.core.model.Task

data class TasksUiState(
    val tasks : List<Task> = emptyList(),
    val isLoading: Boolean = true
)