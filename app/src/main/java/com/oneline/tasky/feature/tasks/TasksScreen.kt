package com.oneline.tasky.feature.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: TasksViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var taskTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasky") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text("Enter a task") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    viewModel.addTask(taskTitle)
                    taskTitle = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled =  taskTitle.isNotBlank()
            ) {
                Text("Add task")
            }
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Tasks (${uiState.tasks.size})",
                style = MaterialTheme.typography.titleLarge
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
                else if (uiState.tasks.isEmpty()){
                    Text(
                        text = "No tasks yet. Add your first task!",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                else{
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            items = uiState.tasks,
                            key = {it.id}
                        ){
                            task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = {
                                        viewModel.toggleTask(task)
                                    }
                                )
                                Text(
                                    text = task.title,
                                    modifier = Modifier.weight(1f)
                                )

                                TextButton(
                                    onClick = {
                                        viewModel.deleteTask(task.id)
                                    }
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

