package com.oneline.tasky.core.data

import com.oneline.tasky.core.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasks: Flow<List<Task>>

    suspend fun addTask(title: String)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: Int)
}