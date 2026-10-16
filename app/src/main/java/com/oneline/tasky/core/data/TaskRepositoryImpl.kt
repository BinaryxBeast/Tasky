package com.oneline.tasky.core.data

import com.oneline.tasky.core.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TaskRepositoryImpl: TaskRepository {
    private val mutex = Mutex()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override val tasks: Flow<List<Task>> = _tasks.asStateFlow()

    private var nextId = 1

    override suspend fun addTask(title: String) {
        mutex.withLock {
            val task = Task(
                id = nextId++,
                title = title
            )

            _tasks.update {
                currentTasks ->
                currentTasks+ task
            }
        }
    }

    override suspend fun updateTask(task: Task) {
        mutex.withLock {
            _tasks.update {
                currentTasks->
                currentTasks.map {
                    existingTask->
                    if (existingTask.id == task.id) task
                    else existingTask
                }
            }
        }
    }

    override suspend fun deleteTask(taskId: Int) {
        mutex.withLock {
            _tasks.update {
                currentTasks ->
                currentTasks.filterNot { it.id == taskId }
            }
        }
    }
}