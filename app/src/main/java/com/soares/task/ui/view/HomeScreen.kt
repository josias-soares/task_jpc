package com.soares.task.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.soares.task.domain.models.Task

const val ARG_TASK_ID = "task"

@Composable
fun HomeScreen(
    tasks: List<Task>?,
    onAddTask: (() -> Unit)?,
    onRemoveTask: ((Task) -> Unit)?,
    onEditTask: ((Task) -> Unit)?,
    onCompleteTask: ((Task) -> Unit)?,
) {
    tasks?.apply {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)

        ) {
            items(

                items = tasks,
                itemContent = {
                    Row {
                        Column {
                            TaskListItem(it, onRemoveTask, onEditTask , onCompleteTask)
                        }
                    }
                })
        }
    }
}

@Composable
fun TaskListItem(
    task: Task?,
    onRemoveTask: ((Task) -> Unit)?,
    onEditTask: ((Task) -> Unit)?,
    onCompleteTask: ((Task) -> Unit)?,
) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                task?.let {
                    Text(text = task.description, style = typography.h6, modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onEditTask?.invoke(task) })
                    )
                    Text(text = "VIEW DETAIL", style = typography.caption)
                }
            }
        }
    }
}