package com.oneline.tasky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oneline.tasky.core.data.TaskRepositoryImpl
import com.oneline.tasky.feature.tasks.TaskScreen
import com.oneline.tasky.feature.tasks.TaskViewModelFactory
import com.oneline.tasky.feature.tasks.TasksViewModel
import com.oneline.tasky.ui.theme.TaskyTheme

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        TaskRepositoryImpl()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskyTheme {
                val viewModel: TasksViewModel = viewModel(
                    factory = TaskViewModelFactory(repository)
                )
                TaskScreen(viewModel = viewModel)
                }
            }
        }
    }

